<%@page import="javax.jdo.*,java.util.Iterator,guestbook.*,guestbook.pc.*" %>
<%@page isThreadSafe="false" %> <%-- more efficient and safe in
                                     a website which is not too loaded --%>

<%! static { WebAppMgr.enhanceAll(); } %>
    
<html>
<head><title>
    Guest Book - ObjectDB for Java/JDO - Web Application Demo
</title></head>
<body> <%

// Obtain a PersistenceManager instance:
PersistenceManager pm = WebAppMgr.getPersistenceManager(application);

try {
    // Handle a filled form (if any):
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    if (firstName != null && lastName != null)
    {
        pm.currentTransaction().begin();
        pm.makePersistent(new Guest(firstName,lastName));
        pm.currentTransaction().commit();
    } %>

    <h1>The Guest Book</h1>
    <p><b>ObjectDB for Java/JDO - Web Application Demo</b></p>
    <hr>

    <form method="POST" action="index.jsp">
        First Name: <input type="text" name="firstName"> &nbsp;&nbsp;
        Last Name: <input type="text" name="lastName"> &nbsp;&nbsp;
        <input type="submit" value="Add">
    </form>

    <hr><ol> <%
    // Display the list of guests:
    Extent extent = pm.getExtent(Guest.class, false);
    Iterator itr = extent.iterator();
    while (itr.hasNext()) { %>
        <li> <%= itr.next() %> </li> <%
    }
    extent.close(itr); %>
    </ol><hr> <%

} finally {
    // Close the PersistenceManager:
    if (pm.currentTransaction().isActive())
        pm.currentTransaction().rollback();
    pm.close();
} %>

<font size="-1">Powered by
<a href="http://www.objectdb.com" target="_blank">ObjectDB Java/JDO Database</a>
</font>

</body>
</html>
