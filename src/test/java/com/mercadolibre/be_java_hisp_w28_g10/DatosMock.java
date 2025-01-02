package com.mercadolibre.be_java_hisp_w28_g10;

import com.mercadolibre.be_java_hisp_w28_g10.model.FollowRelation;
import com.mercadolibre.be_java_hisp_w28_g10.model.Post;
import com.mercadolibre.be_java_hisp_w28_g10.model.Product;
import com.mercadolibre.be_java_hisp_w28_g10.model.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DatosMock {

    public static final List<User> USER_LIST = Arrays.asList(
            new User(1, "Alice"),
            new User(2, "Ron"),
            new User(3, "Pepe"),
            new User(4, "Diana"),
            new User(5, "Eve"),
            new User(6, "Frank"),
            new User(7, "Grace"),
            new User(8, "Hannah"),
            new User(9, "Isaac"),
            new User(10, "Jack"));

    public static final List<Product> PRODUCT_LIST = Arrays.asList(
            new Product(1, "Smartphone Galaxy S21", "Electrónica", "Samsung", "Gris Fantasma", "Smartphone de alto rendimiento con 128GB de almacenamiento."),
            new Product(2, "Laptop XPS 13", "Computadoras", "Dell", "Plata", "Laptop ultradelgada con 16GB de RAM y 512GB de SSD."),
            new Product(3, "Auriculares Inalámbricos", "Audio", "Sony", "Negro", "Auriculares con cancelación de ruido y 30 horas de batería."),
            new Product(4, "Mouse Gamer G502", "Accesorios", "Logitech", "Negro", "Mouse gaming inalámbrico de alta precisión."),
            new Product(5, "Reloj Inteligente Serie 6", "Dispositivos Portátiles", "Apple", "Gris Espacial", "Funciones como monitoreo del corazón y GPS."),
            new Product(6, "Televisor Ultra HD 4K", "Electrónica", "LG", "Negro", "Pantalla de 55 pulgadas con soporte HDR."),
            new Product(7, "Altavoz Bluetooth Portátil", "Audio", "JBL", "Azul", "Resistente al agua con 12 horas de reproducción."),
            new Product(8, "Cámara Digital EOS M50", "Cámaras", "Canon", "Negro", "Cámara compacta sin espejo con 24MP."),
            new Product(9, "Freidora de Aire Pro", "Electrodomésticos", "Philips", "Blanco", "Fríe, hornea, asa y rostiza con poco o ningún aceite."),
            new Product(10, "Pulsera de Actividad", "Dispositivos Portátiles", "Fitbit", "Negro", "Monitorea la frecuencia cardíaca y patrones de sueño."),
            new Product(11, "Silla Gamer", "Muebles", "Arozzi", "Rojo", "Diseño ergonómico óptimo para largas sesiones de juego."),
            new Product(12, "Hervidor Eléctrico", "Electrodomésticos", "Breville", "Acero Inoxidable", "Hervido rápido con control de temperatura."),
            new Product(13, "Tocadiscos de Vinilo", "Audio", "Audio-Technica", "Madera", "Reproduce y convierte discos de vinilo a digital."),
            new Product(14, "Tapete de Yoga", "Fitness", "Liforme", "Morado", "Tapete ecológico y antideslizante para entusiastas del yoga."),
            new Product(15, "Cepillo de Dientes Eléctrico", "Cuidado Personal", "Oral-B", "Blanco", "Con sensores de presión inteligentes."),
            new Product(16, "Adaptador Bluetooth para Auto", "Accesorios", "Anker", "Negro", "Transmite música y realiza llamadas manos libres."),
            new Product(17, "Base de Carga Inalámbrica", "Accesorios", "Belkin", "Blanco", "Carga rápida compatible con dispositivos habilitados para Qi."),
            new Product(18, "Auriculares Inalámbricos con Cancelación de Ruido", "Audio", "Bose", "Negro", "Calidad de sonido excepcional y cancelación de ruido."),
            new Product(19, "Silla de Oficina Ergonómica", "Muebles", "Herman Miller", "Negro", "Soporte para buena postura con características ajustables."),
            new Product(20, "Auriculares Gamer Inalámbricos", "Audio", "Razer", "Verde", "Audio inmersivo con soporte para sonido envolvente."));

    public static final List<Post> POST_LIST = Arrays.asList(
            new Post(5, 1, LocalDate.parse("2024-12-25"),
                    100, 1500.50, new Product(101, "Silla Gamer", "Gamer", "Racer", "Red & Black", "edición especial"), true, 0.25),

            new Post(5, 2, LocalDate.parse("2024-12-17"),
                    101, 120.75, new Product(102, "Teclado Mecánico RGB", "Gaming", "Cooler Master", "Black", "Con retroiluminación RGB"), false, 0.10),

            new Post(3, 3, LocalDate.parse("2024-01-28"),
                    102, 220.00, new Product(103, "Auriculares Inalámbricos", "Audio", "Sony", "Blue", "Con cancelación de ruido"), true, 0.15),

            new Post(4, 4, LocalDate.parse("2024-02-02"),
                    103, 350.00, new Product(104, "Monitor 144Hz", "Gaming", "Acer", "Black", "Con panel IPS"), false, 0.05),

            new Post(5, 5, LocalDate.parse("2024-02-14"),
                    104, 75.99, new Product(105, "Mouse Gamer", "Accessories", "Logitech", "Black", "Alto rendimiento con 12 botones programables"), true, 0.20),

            new Post(6, 6, LocalDate.parse("2024-12-16"),
                    105, 99.99, new Product(106, "Microfono USB", "Audio", "Blue", "Silver", "Ideal para streamers"), false, 0.00),

            new Post(7, 7, LocalDate.parse("2024-03-10"),
                    106, 1200.00, new Product(107, "Laptop Gaming", "Computers", "Dell", "Black", "Con GPU de alta gama"), true, 0.10),

            new Post(8, 8, LocalDate.parse("2024-03-29"),
                    107, 70.00, new Product(108, "Pingüino de Refrigeración", "Cooler", "Thermaltake", "Black", "Refrigerador eficiente para CPU"), true, 0.15),

            new Post(9, 9, LocalDate.parse("2024-04-15"),
                    108, 300.00, new Product(109, "Gafas de Realidad Virtual", "Gaming", "Oculus", "Black", "Inmersión total en juegos"), false, 0.00),

            new Post(10, 10, LocalDate.parse("2024-05-02"),
                    109, 600.00, new Product(110, "Silla Ergonómica", "Furniture", "Herman Miller", "Grey", "Ideal para largas horas de trabajo"), true, 0.10),

            new Post(1, 11, LocalDate.parse("2024-05-08"),
                    100, 25.99, new Product(111, "Hub USB-C", "Accessories", "Anker", "White", "Con 3 puertos USB 3.0"), false, 0.05),

            new Post(2, 12, LocalDate.parse("2024-05-20"),
                    101, 45.00, new Product(112, "Bolsa Térmica", "Accessories", "Igloo", "Blue", "Mantiene tus bebidas frías por horas."), true, 0.10),

            new Post(3, 13, LocalDate.parse("2024-05-25"),
                    102, 120.00, new Product(113, "Cámara de Seguridad", "Cameras", "Ring", "White", "Con visión nocturna y alertas."), false, 0.00),

            new Post(4, 14, LocalDate.parse("2024-05-30"),
                    103, 500.00, new Product(114, "Bicicleta de Montaña", "Bikes", "Trek", "Green", "Aventura en cada montaña."), true, 0.15),

            new Post(5, 15, LocalDate.parse("2024-06-03"),
                    104, 25.00, new Product(115, "Cargador Inalámbrico", "Accessories", "Belkin", "Black", "Compatible con todos los smartphones."), false, 0.00),

            new Post(6, 16, LocalDate.parse("2024-06-10"),
                    105, 150.00, new Product(116, "Altavoz Portátil", "Audio", "Bose", "Black", "Resistencia al agua, duración de 12 horas."), false, 0.00),

            new Post(7, 17, LocalDate.parse("2024-06-12"),
                    106, 350.00, new Product(117, "Cámara de Acción", "Cameras", "GoPro", "Black", "Resistente al agua y a prueba de golpes."), true, 0.10),

            new Post(8, 18, LocalDate.parse("2024-06-10"),
                    107, 399.00, new Product(118, "Smartwatch", "Wearables", "Apple", "Gold", "Rastrea la actividad física."), true, 0.20),

            new Post(9, 19, LocalDate.parse("2024-06-15"),
                    108, 90.00, new Product(119, "Kettle Eléctrico", "Kitchen", "Breville", "Silver", "Con control de temperatura."), false, 0.00),

            new Post(10, 20, LocalDate.parse("2024-06-20"),
                    109, 199.99, new Product(120, "Tocadiscos", "Audio", "Crosley", "Red", "Reproduce tus discos de vinilo."), true, 0.15));

    public static FollowRelation FOLLOW_RELATION = new FollowRelation(1, 2);

    public static List<FollowRelation> FOLLOW_RELATIONS = Arrays.asList(
            new FollowRelation(1, 2),
            new FollowRelation(1, 3),
            new FollowRelation(1, 4),
            new FollowRelation(2, 5),
            new FollowRelation(2, 6),
            new FollowRelation(2, 7),
            new FollowRelation(2, 4),
            new FollowRelation(3, 4),
            new FollowRelation(5, 4),
            new FollowRelation(5, 1)
    );
    public static List<FollowRelation> FOLLOW_RELATIONS_2 = Arrays.asList(
            new FollowRelation(2, 5),
            new FollowRelation(2, 6),
            new FollowRelation(2, 7),
            new FollowRelation(2, 4)
    );

    public static List<FollowRelation> FOLLOW_RELATIONS_3  = List.of(
            new FollowRelation(2, 1),
            new FollowRelation(3, 1)
        );

    public static List<FollowRelation> FOLLOW_RELATIONS_4 = List.of(
            new FollowRelation(1, 2),
            new FollowRelation(1, 3)
    );

    public static final User USER_1 = new User(1, "Alice");
    public static final User USER_2 = new User(2, "Ron");
    public static final User USER_3 =  new User(3, "Pepe");
    public static final User USER_5 = new User(5, "Eve");

}
