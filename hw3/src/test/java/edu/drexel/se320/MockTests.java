package edu.drexel.se320;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.util.List;
import java.io.IOException;

public class MockTests {

    public MockTests() {}

    /**
     * Demonstrate a working mock from the Mockito documentation.
     * https://static.javadoc.io/org.mockito/mockito-core/3.1.0/org/mockito/Mockito.html#1
     */
    @Test
    public void testMockDemo() {
         List<String> mockedList = (List<String>)mock(List.class);

         mockedList.add("one");
         mockedList.clear();

         verify(mockedList).add("one");
         verify(mockedList).clear();
    }

    @Test
    public void testServerConnectionFailureGivesNull() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(false);

        // If you change the code to pass the mock above to the client (based on your choice of
        // refactoring), this test should pass.  Until then, it will fail.
        assertNull(c.requestFile("test", "test"));
    }

    @Test
    public void testClientCallsNoOtherMethodsWhenConnectToFails() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(false);
        c.requestFile("test","test");

        // Verify the client code calls no further methods on the connection.
        verify(c.server, never()).requestFileContents(anyString());
        verify(c.server, never()).closeConnection();
        verify(c.server, never()).moreBytes();
        verify(c.server, never()).read();
    }

    @Test
    public void testCloseConnectionCalledWhenNoValidFile() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true);
        when(c.server.requestFileContents(anyString())).thenReturn(false);
        c.requestFile("test","test");

        //Test that if the connection succeeds but there is no valid file of that name, the client code calls no further methods on the connection except closeConnection
        verify(c.server, times(1)).closeConnection();
        verify(c.server, never()).requestFileContents(anyString());
        verify(c.server, never()).moreBytes();
        verify(c.server, never()).read();
    }

    @Test
    //Test that if the connection succeeds and the file is valid and non-empty, that the connection asks for at least some part of the file.
    public void testAsksForPartOfFile() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert successful connection
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert file exists
        when(c.server.moreBytes()).thenReturn(true).thenReturn(false); //assert file is not empty
        c.requestFile("dummy","dummy");

        //Test that the connection asks for at least some part of the file.
        verify(c.server, atLeastOnce()).read();

    }

    //Test that if the connection succeeds and the file is valid but empty, the client returns an empty string
    @Test
    public void testEmptyFile() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert successful connection
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert file exists
        when(c.server.moreBytes()).thenReturn(false); //assert file is empty


        //Test that the client returns an empty string
        assertEquals("",c.requestFile("dummy","dummy"));
    }

    @Test
    public void testReadErrorException() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert successful connection
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert file exists
        when(c.server.moreBytes()).thenReturn(true); //assert file is not empty
        when(c.server.read()).thenReturn(anyString()).thenThrow(new IOException()); //assert file is in the middle of being read and expection is thrown

        //Test  client still returns null to indicate an error, rather than returning a partial result.
        assertNull(c.requestFile("test", "test"));
    }

    // Test that if the initial server connection succeeds, then if a IOException occurs while retrieving the file (requesting, or reading bytes, either one) the client still explicitly closes the server connection.
    @Test
    public void testErrorThrownAfterSucessfulConnection() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert successful connection
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert file exists
        when(c.server.moreBytes()).thenThrow(new IOException()); // assert exception thrown when attempting to get bytes


        c.requestFile("test", "test");

        //Test that client closes server connection when exception thrown while getting bytes
        verify(c.server, atLeastOnce()).closeConnection();

        when(c.server.connectTo(anyString())).thenReturn(true); //assert successful connection
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert file exists
        when(c.server.moreBytes()).thenReturn(true); // assert get bytes successful
        when(c.server.read()).thenThrow(new IOException()); //assert file throws exception when reading bytes

        c.requestFile("test", "test");

        //Test that client closes server connection when exception thrown while reading bytes
        verify(c.server, atLeastOnce()).closeConnection();
    }

    @Test
    public void testOverideReturnResult() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert successful connection
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert file exists
        when(c.server.moreBytes()).thenReturn(true).thenReturn(false); // assert there is bytes to read for one iteration of while loop
        when(c.server.read()).thenReturn("overide testing overide behavior"); //assert that file starts with overide

        //Test that the client simply returns unmodified the contents if it reads a file from the server whose contents start with "override"
        assertEquals(c.server.read(),c.requestFile("test","test"));
    }

    @Test
    public void testReadCalledInOrder() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert successful connection
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert file exists
        when(c.server.moreBytes()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false); // assert there is bytes to read 4 times
        when(c.server.read()).thenReturn("1").thenReturn("2").thenReturn("3").thenReturn("4"); // assert what is read for the 4 times


        //If the server returns the file in four pieces (i.e., four calls to read() must be executed), the client concatenates them in the correct order).
        assertEquals("1234",c.requestFile("test","test"));

    }

    @Test
    public void testReadReturnsNull() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert successful connection
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert file exists
        when(c.server.moreBytes()).thenReturn(true).thenReturn(true).thenReturn(false); // assert there is bytes to read 2 times
        when(c.server.read()).thenReturn("asdf").thenReturn(null); // assert what is read for the 2 times


        //If read() ever returns null, the client treats this as the empty string.
        assertEquals("asdf",c.requestFile("test","test"));

    }

    @Test
    public void testConnectToFailsFirstExecution() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenThrow(new IOException()); //assert connectTo() throws exception

        //Test that client returns null if connectTo() fails at its first execution
        assertNull(c.requestFile("test", "test"));

    }

    @Test
    public void testRequestFileContentsFailsFirstExecution() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert connection is successful
        when(c.server.requestFileContents(anyString())).thenThrow(new IOException()); //assert requestFileContents() fails

        //Test that client returns null if requestFileContents() fails at its first execution
        assertNull(c.requestFile("test", "test"));

    }

    @Test
    public void testMoreBytesFailsFirstExecution() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert connection is successful
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert that file exists
        when(c.server.moreBytes()).thenThrow(new IOException()); //assert moreBytes() fails

        //Test that client returns null if moreBytes() fails at its first execution
        assertNull(c.requestFile("test", "test"));

    }

    @Test
    public void testReadFailsFirstExecution() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert connection is successful
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert that file exists
        when(c.server.moreBytes()).thenReturn(true); //assert that there are more bytes to read
        when(c.server.read()).thenThrow(new IOException()); //assert read() fails

        //Test that client returns null if read() fails at its first execution
        assertNull(c.requestFile("test", "test"));

    }

    @Test
    public void testCloseConnectionFirstExecution() throws IOException {
        Client c = new Client();
        when(c.server.connectTo(anyString())).thenReturn(true); //assert connection is successful
        when(c.server.requestFileContents(anyString())).thenReturn(true); //assert that file exists
        when(c.server.moreBytes()).thenReturn(false); //assert that there are  no more bytes to read
        doThrow(new IOException()).when(c.server).closeConnection(); //assert closeConnection() fails

        //Test that client returns null if closeConnection() fails at its first execution
        assertNull(c.requestFile("test", "test"));

    }

}
