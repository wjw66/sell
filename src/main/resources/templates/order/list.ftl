<html>
<head>
    <meta charset="utf-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered">
                <thead>
                <tr class="success">
                    <th>订单ID</th>
                    <th>顾客姓名</th>
                    <th>顾客手机号</th>
                    <th>订单地址</th>
                    <th>订单金额</th>
                    <th>订单状态</th>
                    <th>支付状态</th>
                    <th>创建时间</th>
                    <th colspan="2">操作</th>
                </tr>
                </thead>
                <tbody>
                <#list  orderDTOPage.content as orderDTO>

                    <tr>
                        <td>${orderDTO.orderId}</td>
                        <td>${orderDTO.buyerName}</td>
                        <td>${orderDTO.buyerPhone}</td>
                        <td>${orderDTO.buyerAddress}</td>
                        <td>${orderDTO.orderAmount}</td>

                        <td>${orderDTO.orderStatus}</td>

                        <td>${orderDTO.payStatus}</td>
                        <td>${orderDTO.createTime}</td>
                        <td><a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a></td>
                        <td>
                            <#if orderDTO.getOrderStatusEnum().message == "新下单">
                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                            </#if>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
    <#--分页-->
    <div class="col-md-12 column">
        <ul class="pagination pull-right" >
            <#--如果当前页小于等于第一页-->
            <#if currentPage lte 1>
            <li class="disabled"><a href="#">上一页</a></li>
            <#else ><li><a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
            </#if>
            <#list 1..orderDTOPage.getTotalPages() as index >
                <#if currentPage == index>
            <li class="disabled"><a href="#">${index}</a></li>
                <#else ><li>
                <a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
            </li>
                </#if>
            </#list>
            <#--如果当前页大于等于总页数-->
            <#if currentPage gte orderDTOPage.getTotalPages()>
            <li class="disabled"><a href="#">下一页</a></li>
            <#else ><li><a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
            </#if>
        </ul>
    </div>
</div>
</body>
</html>