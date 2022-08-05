# labs access controls vulnerabilities

## Lab: Unprotected admin functionality
This lab has an unprotected admin panel.

Solve the lab by deleting the user carlos.

Encontramos con un mapeo robots.txt y dentro aparece la url administrator-panel, el cual no tiene protección

Borramos carlos.

![](assets/2022-07-20-07-32-03.png)

## Lab: Unprotected admin functionality with unpredictable URL

This lab has an unprotected admin panel. It's located at an unpredictable location, but the location is disclosed somewhere in the application.

Solve the lab by accessing the admin panel, and using it to delete the user carlos.

Vamos a my-account y miramos el código

```js
var isAdmin = false;
if (isAdmin) {
   var topLinksTag = document.getElementsByClassName("top-links")[0];
   var adminPanelTag = document.createElement('a');
   adminPanelTag.setAttribute('href', '/admin-xkqtid');
   adminPanelTag.innerText = 'Admin panel';
   topLinksTag.append(adminPanelTag);
   var pTag = document.createElement('p');
   pTag.innerText = '|';
   topLinksTag.appendChild(pTag);
}
</script>
```

![](assets/2022-07-20-07-37-26.png)

## Lab: User role controlled by request parameter

This lab has an admin panel at /admin, which identifies administrators using a forgeable cookie.

Solve the lab by accessing the admin panel and using it to delete the user carlos.

You can log in to your own account using the following credentials: wiener:peter

Nos logueamos con wiener, en burpsuite vemos

![](assets/2022-07-20-07-42-10.png)

Tenemos que cambiarlo a true y enviamos desde repeater.

una vez que está en true cambiamos la url por /admin pudiendo ver las url de eliminación del usuario

![](assets/2022-07-20-07-44-04.png)

Copiamos la url de eliminación y enviamos

![](assets/2022-07-20-07-44-36.png)

![](assets/2022-07-20-07-44-46.png)

## Lab: User role can be modified in user profile

This lab has an admin panel at /admin. It's only accessible to logged-in users with a roleid of 2.

Solve the lab by accessing the admin panel and using it to delete the user carlos.

You can log in to your own account using the following credentials: wiener:peter

Es igual que el de antes sino que en vez de aparecer isadmin... te aparece el rol y habrá que cambiarlo a 2

El sitio que parece que es para cambiarlo es cuando realizas un cambio de mail

![](assets/2022-07-20-08-36-28.png)

![](assets/2022-07-20-08-37-40.png)

Pasamos en el json tambien el rolid=2

![](assets/2022-07-20-08-38-30.png)

Ahora ponemos admin en la url, y luego la url de eliminacion de carlos

![](assets/2022-07-20-08-39-11.png)

![](assets/2022-07-20-08-39-21.png)

## Lab: URL-based access control can be circumvented

This website has an unauthenticated admin panel at /admin, but a front-end system has been configured to block external access to that path. However, the back-end application is built on a framework that supports the X-Original-URL header.

To solve the lab, access the admin panel and delete the user carlos.

- add X-Original-URL 

Añadimos el X-Original-URL: /admin

Al enviar nos deja entrar en admin

![](assets/2022-07-20-09-00-18.png)

Cambiamos el xriginal por la de borrar a carlos

![](assets/2022-07-20-09-01-04.png)

Vemos que nos deja por que le hace falta el parámetro. Pongo arriba en la url el parámetro ?username=carlos ya que hemos visto que ese era el parámetro de la url

![](assets/2022-07-20-09-02-14.png)

![](assets/2022-07-20-09-02-24.png)

## Lab: Method-based access control can be circumvented

This lab implements access controls based partly on the HTTP method of requests. You can familiarize yourself with the admin panel by logging in using the credentials administrator:admin.

To solve the lab, log in using the credentials wiener:peter and exploit the flawed access controls to promote yourself to become an administrator.

- administrator:admin
- wiener:peter

```mermaid
flowchart TD
A[Nos logueamos como Admin</br>Capturamos el admin-roles</br>Upgrade wiener</br>Cambiamos a POST]
B[Abrimos un laboratorio nuevo]-->C[Nos logueamos como wiener]-->D[Capturamos la cookie]
A-->D-->E[Usamos la cookie de wiener en la pagina interceptada como admin]

```


![](assets/2022-08-05-11-46-53.png)

## Lab: User ID controlled by request parameter

This lab has a horizontal privilege escalation vulnerability on the user account page.

To solve the lab, obtain the API key for the user carlos and submit it as the solution.

You can log in to your own account using the following credentials: wiener:peter



No hace falta nada mas que logueranos, vemos en myaccount arriba el id de wiener, lo cambiamos por carlos , actualizamos y mandamos la apikey

![](assets/2022-08-05-12-02-28.png)




## Lab: User ID controlled by request parameter, with unpredictable user IDs

Esta opción no es tan obvia, pero podemos encontrar la guid de carlos en un post del blog por ejemplo

```html
blogs?userId=259ac446-1644-4e8b-8f0d-eadf7047350d
```

Cambiamos en la url de myaccount la guid de carlos y resolvemos enviando la apikey

![](assets/2022-08-05-12-07-21.png)

## Lab: User ID controlled by request parameter with data leakage in redirect
This lab contains an access control vulnerability where sensitive information is leaked in the body of a redirect response.

To solve the lab, obtain the API key for the user carlos and submit it as the solution.

You can log in to your own account using the following credentials: wiener:peter

En este caso cambiando la id por carlos no nos funciona directamente , pero si lo capturamos con burpsuite podemos ver el código antes de que nos redireccione

![](assets/2022-08-05-12-13-24.png)

![](assets/2022-08-05-12-13-42.png)