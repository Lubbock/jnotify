package com.lame.jnotify.netty.httpproxy;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            DefaultHttpRequest request = (DefaultHttpRequest) msg;
            String uri = request.uri();
            if ("/favicon.ico".equals("uri")) {
                return;
            }
            log.info(new Date().toString());
            String s = (String) MemCache.get(uri);
            if (s == null || s.length() == 0) {
                try {
                    URL url = new URL("http://127.0.0.1:3000" + uri);
                    log.info(url.toString());
                    URLConnection urlConnection = url.openConnection();
                    HttpURLConnection connection = (HttpURLConnection) urlConnection;
                    connection.setRequestMethod("GET");
                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                        StringBuilder bs = new StringBuilder();
                        String l;
                        while ((l = bufferedReader.readLine()) != null) {
                            bs.append(l).append("\n");
                        }
                        s = bs.toString();
                    }
                    MemCache.put(uri, s);
                    connection.disconnect();
                } catch (Exception e) {
                    log.error("", e);
                    return;
                }
            }else {

            }
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(s != null ? s.getBytes() : new byte[0])
            );
            response.headers().set("content-type", "text/html");
            response.headers().set("content-length", response.content().readableBytes());
            response.headers().set("connection", HttpHeaderValues.KEEP_ALIVE);
            ctx.write(response);
            ctx.flush();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
