package net.ravinvt.webMC;

import fi.iki.elonen.NanoHTTPD;

public class SimpleWebServer extends NanoHTTPD {
    public SimpleWebServer(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        return newFixedLengthResponse("Nothing here");
    }
}
