<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>File Upload to Database Demo</title>
</head>
<body>
    <center>
        <h1>File Upload to Database Demo</h1>
        <form method="post" action="upload" enctype="multipart/form-data">
            <table border="0">
                <tr>
                    <td>First Name: </td>
                    <td><input type="text" name="firstName" size="50"/></td>
                </tr>
                <tr>
                    <td>Last Name: </td>
                    <td><input type="text" name="lastName" size="50"/></td>
                </tr>
                <tr>
                    <td>Portrait Photo: </td>
                    <td><input type="file" name="photo" size="50"/></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Save">
                    </td>
                </tr>
            </table>
        </form>
    </center>
</body>
</html>