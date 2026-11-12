package org.example.api;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.Charset;

@Component
public class XslXmlFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        chain.doFilter(request, responseWrapper);
        String stylesheet = getStylesheet(request);
        String contentType = responseWrapper.getContentType();

        // Обрабатываем только XML-ответы для известных REST endpoint
        if (stylesheet != null && contentType != null && contentType.toLowerCase().contains("xml")) {
            byte[] originalBody = responseWrapper.getContentAsByteArray();

            if (originalBody.length > 0) {
                Charset charset = Charset.forName(responseWrapper.getCharacterEncoding());
                String xml = new String(originalBody, charset);

                // Не добавляем инструкцию повторно
                if (!xml.contains("<?xml-stylesheet")) {
                    String instruction = "<?xml-stylesheet type=\"text/xsl\" href=\"" + stylesheet + "\"?>\n";

                    int declarationEnd = xml.startsWith("<?xml") ? xml.indexOf("?>") + 2 : -1;

                    if (declarationEnd > 0) {
                        xml = xml.substring(0, declarationEnd) + "\n" + instruction + xml.substring(declarationEnd);
                    }
                    else {
                        xml = instruction + xml;
                    }

                    byte[] modifiedBody = xml.getBytes(charset);

                    responseWrapper.resetBuffer();
                    responseWrapper.setContentLength(modifiedBody.length);
                    responseWrapper.getOutputStream().write(modifiedBody);
                }
            }
        }
        responseWrapper.copyBodyToResponse();
    }

    private String getStylesheet(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Списки покупателей и заказов
        if (path.equals("/api/customers") || path.equals("/api/customers/")) {
            return "/xsl/customers.xsl";
        }

        if (path.matches("/api/customers/[^/]+/?")) {
            return "/xsl/customer.xsl";
        }

        if (path.equals("/api/orders")
                || path.equals("/api/orders/")) {
            return "/xsl/orders.xsl";
        }

        if (path.matches("/api/orders/[^/]+/?")) {
            return "/xsl/order.xsl";
        }
        return null;
    }
}
