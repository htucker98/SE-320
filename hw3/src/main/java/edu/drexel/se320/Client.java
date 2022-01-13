package edu.drexel.se320;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.lang.StringBuilder;
import static org.mockito.Mockito.*;

public class Client {

    private String lastResult;
    StringBuilder sb = null;
    public ServerConnection server;

    public Client() {
        lastResult = "";
        this.server = createMockServer();

    }
    protected ServerConnection createMockServer() {

        return mock(ServerConnection.class);
    }

    public String requestFile(String server, String file) {
        if (server == null)
            throw new IllegalArgumentException("Null server address");
        if (file == null)
            throw new IllegalArgumentException("Null file");

	// This ServerConnection is here only as a placeholder --- the real dependency
	// doesn't exist yet.  Your tests will need to make sure the code below this
	// definition of conn interacts with connections correctly despite not having
	// a real ServerConnection.
	//
	// To be clear: do NOT implement the methods below.  Instead, make it possible
	// to run the code below with a mock, rather than this dummy implementation.

	// We'll use a StringBuilder to construct large strings more efficiently
	// than repeated linear calls to string concatenation.
        sb = new StringBuilder();

        try {
            if (this.server.connectTo(server)) {
                boolean validFile = this.server.requestFileContents(file);
                if (validFile) {
                    while (this.server.moreBytes()) {
                        String tmp = this.server.read();
                        if (tmp != null) {
                            sb.append(tmp);
                        }
                    }
                    this.server.closeConnection();
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }

        String result = sb.toString();
        lastResult = result;
        return result;
    }

}

