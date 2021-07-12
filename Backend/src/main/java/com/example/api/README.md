# REST API

This package contains the source code for the RESTful API service, including the endpoints, detailed below:

## `/user`

Endpoints relating to the user's account:

<table>
<tr>
<th align="center">Name</th>
<th align="center">Request Body</th>
<th align="center">Response</th>
<th align="center">Description</th>
</tr>

<tr>
<td align="center"><pre>login/</pre></td>
<td><pre>
{
    "username": String,
    "password": String
}
</pre></td>
<td><pre>
{
    "username": String,
    "accessToken": String
}
</pre></td>
<td>Authenticates a user using JWT and returns the access token to log them into the web app. Returns a <code>401</code> if authentication fails.</td>
</tr>

<tr>
<td align="center"><pre>register/</pre></td>
<td><pre>
{
    "username": String,
    "password": String
}
</pre></td>
<td><pre>
{
    "username": String,
    "message": String,
    "code: String
}
</pre></td>
<td>Register a new user. Returns a <code>401</code> if registration fails.</td>
</tr>


<tr>
<td align="center"><pre>change-password/</pre></td>
<td><pre>
{
    "username": String,
    "password": String,
    "newPassword": String
}
</pre></td>
<td><pre>
{
    "message": String,
    "code": String
}
</pre></td>
<td>Changes the password for a given user. Returns a <code>401</code> if authentication fails.</td>
</tr>

<tr>
<td align="center"><pre>delete/</pre></td>
<td><pre>
{
    "username": String,
    "password": String
}
</pre></td>
<td><pre>
{
    "message": String,
    "code": String
}
</pre></td>
<td>Delete a user's account. Returns a <code>401</code> if authentication fails.</td>
</tr>
</table>