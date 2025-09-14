# 📦 Gestor de Ventas de Expositores

## 📌 Descripción
Este proyecto es un **gestor de ventas para expositores de souvenirs en España**.  
El sistema permite llevar un control detallado tanto del **movimiento económico** como del **inventario de souvenirs**, registrando:  

- Qué productos entran y salen de cada expositor.  
- Quién realizó la venta.  
- A qué cliente se le vendió.  

De esta forma, los administradores pueden tener una visión clara de la actividad de los expositores y la rentabilidad de cada punto de venta.  

---

## ✨ Funcionalidades principales
- 🏷️ Gestión de **souvenirs** (alta, baja, modificación).  
- 💰 Control de **ventas** (registro de vendedor, cliente y producto).  
- 📊 Control de **movimientos económicos** asociados a las ventas.  
- 🏪 Registro de **expositores** distribuidos por España.  
- 📈 Informes sobre el estado de inventario y ventas realizadas.  

---

## 🛠️ Tecnologías utilizadas
- **Java 17**  
- **Spring Boot 3.x** (API REST)  
- **Maven** (gestor de dependencias)  
- **PostgreSQL / MySQL** (base de datos relacional)  
- **Hibernate / JPA** (persistencia de datos)

## 🗂️ Modelo Entidad-Relación

```mermaid
erDiagram
    VENDEDOR ||--o{ VENTA : realiza
    CLIENTE ||--o{ VENTA : recibe
    VENTA ||--|{ LINEA_VENTA : contiene
    EXPOSITOR ||--o{ VENTA : registra
    SOUVENIR ||--o{ LINEA_VENTA : incluye

    VENDEDOR {
        Long id
        String nombre
        String password
    }
    CLIENTE {
        Long id
        String nombre
        String email
    }
    EXPOSITOR {
        Long id
        String ubicacion
    }
    SOUVENIR {
        Long id
        String nombre
        Double precio
    }
    VENTA {
        Long id
        Date fecha
        Double total
    }
    LINEA_VENTA {
        Long id
        Integer cantidad
        Double subtotal
    }

