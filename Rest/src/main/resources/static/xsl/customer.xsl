<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Информация о покупателе</title>
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
                    <h1>Информация о покупателе</h1>
                    <p><b>ID:</b> <xsl:value-of select="/*/id"/></p>
                    <p><b>Имя:</b> <xsl:value-of select="/*/name"/></p>
                    <p><b>Email:</b> <xsl:value-of select="/*/email"/></p>
                    <p><b>Телефон:</b> <xsl:value-of select="/*/phone"/></p>

                    <p>
                        <a href="/api/customers">К списку покупателей</a>
                    </p>
                    <p>
                        <a href="/api/orders">К списку заказов</a>
                    </p>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
