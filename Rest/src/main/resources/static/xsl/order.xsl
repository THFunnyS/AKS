<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Информация о заказе</title>
                <style>
                    body {
                    font-family: Arial, sans-serif;
                    margin: 40px;
                    background: #f4f6f9;
                    color: #263238;
                    }
                    .card {
                    background: white;
                    padding: 25px;
                    border-radius: 8px;
                    max-width: 550px;
                    box-shadow: 0 2px 8px #ccc;
                    }
                    h1 { color: #263b53; }
                    p { font-size: 17px; }
                    a { color: #1769aa; text-decoration: none; }
                    a:hover { text-decoration: underline; }
                </style>
            </head>
            <body>
                <div class="card">
                    <h1>Информация о заказе</h1>
                    <p><b>ID:</b> <xsl:value-of select="/*/id"/></p>
                    <p><b>Дата:</b> <xsl:value-of select="/*/orderDate"/></p>
                    <p><b>Сумма:</b> <xsl:value-of select="/*/totalSum"/></p>
                    <p><b>Статус:</b> <xsl:value-of select="/*/status"/></p>
                    <p>
                        <b>Покупатель:</b>
                        <a>
                            <xsl:attribute name="href">
                                <xsl:text>/api/customers/</xsl:text>
                                <xsl:value-of select="/*/customerId"/>
                            </xsl:attribute>
                            <xsl:value-of select="/*/customerName"/>
                        </a>
                    </p>

                    <p>
                        <a href="/api/orders">К списку заказов</a>
                    </p>
                    <p>
                        <a href="/api/customers">К списку покупателей</a>
                    </p>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
