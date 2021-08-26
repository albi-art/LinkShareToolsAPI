/*
 * Copyright (c) 2021.
 * https://github.com/albi-art/LinkReceiver
 */
package servlets;

import resolvers.HLSLinkTools;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

@WebServlet(name = "hlsServlet", value = "/hls/get-video-stream.m3u8")
public class HlsServlet extends HttpServlet {
    private final HLSLinkTools hlsLinkTools = HLSLinkTools.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String linkHash = request.getParameter("hash");
        if (linkHash == null) {
            System.out.println("status: " + HTTP_BAD_REQUEST);
            response.setStatus(HTTP_BAD_REQUEST);
            return;
        }

        try {
            String sourceLink = hlsLinkTools.getHlsLinkByHash(linkHash);
            System.out.printf("Source link: %s%n", sourceLink);
            if (sourceLink == null) {
                response.setStatus(HTTP_NOT_FOUND);
                return;
            }

            response.sendRedirect(sourceLink);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "HLS tools servlet";
    }
}
