<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output method="html" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Список заказов</title>
                <style>
                    body {
                    font-family: Arial, sans-serif;
                    margin: 40px;
                    background: #f4f6f9;
                    color: #263238;
                    }
                    h1 { color: #263b53; }
                    table {
                    border-collapse: collapse;
                    width: 100%;
                    background: white;
                    }
                    th, td {
                    padding: 12px;
                    border: 1px solid #ddd;
                    text-align: left;
                    }
                    th { background: #34495e; color: white; }
                    tr:nth-child(even) { background: #f2f2f2; }
                    a { color: #1769aa; text-decoration: none; }
                    a:hover { text-decoration: underline; }
                </style>
            </head>
            <body>
                <h1>Список заказов</h1>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Дата</th>
                        <th>Сумма</th>
                        <th>Статус</th>
                        <th>Покупатель</th>
                        <th>Подробнее</th>
                    </tr>
                    <xsl:for-each select="List/item">
                        <tr>
                            <td><xsl:value-of select="id"/></td>
                            <td><xsl:value-of select="orderDate"/></td>
                            <td><xsl:value-of select="totalSum"/></td>
                            <td><xsl:value-of select="status"/></td>
                            <td>
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>/api/customers/</xsl:text>
                                        <xsl:value-of select="customerId"/>
                                    </xsl:attribute>
                                    <xsl:value-of select="customerName"/>
                                </a>
                            </td>
                            <td>
                                <a>
                                    <xsl:attribute name="href">
                                        <xsl:text>/api/orders/</xsl:text>
                                        <xsl:value-of select="id"/>
                                    </xsl:attribute>
                                    Открыть
                                </a>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
                <p>
                    <a href="/api/customers">Перейти к списку покупателей</a>
                </p>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
