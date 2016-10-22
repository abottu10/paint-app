# paint-app

A side project I decided to work on to become familiar with multi-threading and networking in Java. NOT FINISHED!

PaintConnector.java is the client side program which brings up the GUI and handles the connection with the server.

SimplePaint.java is a GUI I found [online](http://math.hws.edu/eck/cs124/javanotes6/source/SimplePaint.java) which is modified to send data to the server side program.

paint_server.java is the server side program which creates threads for each client and updates them as new data is sent to the server.

Issues: only able to view messages sent in the console. Needs functionality that updates the client's screen when the data is recieved.
