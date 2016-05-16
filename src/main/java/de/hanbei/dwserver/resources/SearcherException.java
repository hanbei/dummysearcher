package de.hanbei.dwserver.resources;

import java.io.IOException;

public class SearcherException extends RuntimeException {
    public SearcherException(IOException e) {
        super(e);
    }
}
