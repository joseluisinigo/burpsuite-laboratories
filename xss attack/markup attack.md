# markup attack

# Lab: Reflected XSS protected by very strict CSP, with dangling markup attack

This lab using a strict CSP that blocks outgoing requests to external web sites.

To solve the lab, first perform a cross-site scripting attack that bypasses the CSP and exfiltrates a simulated victim user's CSRF token using Burp Collaborator. You then need to change the simulated user's email address to hacker@evil-user.net.

You must label your vector with the word "Click" in order to induce the simulated user to click it. For example:

<a href="">Click me</a>


You can log in to your own account using the following credentials: wiener:peter

-  perform a cross-site scripting attack that bypasses the CSP and exfiltrates a simulated victim user's CSRF token 
- wiener:peter
- attack <a href="">Click me</a>
- mail hacker@evil-user.net