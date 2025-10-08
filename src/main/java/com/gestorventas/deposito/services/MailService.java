package com.gestorventas.deposito.services;

import com.gestorventas.deposito.models.LineaPedido;
import com.gestorventas.deposito.models.Pedido;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreoPedido(String destinatario, Pedido pedido) throws MessagingException {
        String html = generarHtmlPedido(pedido);

        MimeMessage mensaje = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mensaje, true);

        helper.setTo(destinatario);
        helper.setSubject("Confirmación de pedido #" + pedido.getId());
        helper.setText(html, true); // true para interpretar HTML
        helper.setFrom("tucorreo@empresa.com");

        mailSender.send(mensaje);
    }

    private String generarHtmlPedido(Pedido pedido) {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");
        double total = 0;
        sb.append("<html><body>");
        sb.append("<h2>Confirmación de Pedido #" + pedido.getId() + "</h2>");
        sb.append("<p>Cliente: <b>").append(pedido.getCliente()).append("</b></p>");
        sb.append("<table border='1' cellspacing='0' cellpadding='5'>");
        sb.append("<tr><th>Producto</th><th>Cantidad</th><th>Precio</th><th>Total</th></tr>");

        for (LineaPedido linea : pedido.getLineas()) {
            double subtotal = linea.getCantidad() * linea.getPrecio();
            sb.append("<tr>")
                    .append("<td>").append(linea.getProducto()).append("</td>")
                    .append("<td>").append(linea.getCantidad()).append("</td>")
                    .append("<td>").append(df.format(linea.getPrecio())).append(" €</td>")
                    .append("<td>").append(df.format(subtotal)).append(" €</td>")
                    .append("</tr>");
            total += subtotal;
        }

        sb.append("</table>");
        sb.append("<h3>Total: ").append("TOTAL A CALCULAR").append(" €</h3>");
        sb.append("<p>Gracias por confiar en nuestra empresa.</p>");
        sb.append("</body></html>");

        return sb.toString();
    }
}
