<!DOCTYPE html>
<html lang="en">
<#include 'head.ftl'>
<body>

<#include 'navbar.ftl'>

<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-md-10" style="margin: auto;display: block">
            <br /><br/><br/>
            <h1 class="my-4">👩🏽‍💻All users👨🏽‍💻️</h1>
            <!--Tablas de usuarios -->
            <p style="font-size: 12px">*you can select which users can be admins🔥</p>
            <table class="table">
                <thead>
                <tr>
                    <th>Username</th>
                    <th>Name</th>
                    <th>Admin</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                    <#list users as user>

                        <tr>
                            <form method="post" action="/make-user-admin/${user.username}">
                                <td>${user.username}</td>
                                <td>${user.nombre}</td>
                                <#if user.username == "Admin">
                                    <td><input type="checkbox" name="is-admin" checked disabled> </td>
                                <#elseif user.administrador == true>
                                    <td><input type="checkbox" name="is-admin" checked> </td>
                                <#else>
                                    <td><input type="checkbox" name="is-admin"> </td>
                                </#if>

                                <td><button id="changeStatus" type="submit" class="btn btn-primary" style="background-color: green">Edit</button></td>
                            </form>
                        </tr>
                    </#list>

                </tbody>
            </table>

            <!-- Blog Post -->
            <div class="card mb-4"></div>
        </div>
    </div>
    <!-- /.row -->
</div>
<!-- /.container -->

<#--<script type="text/javascript">-->
<#--    $(document).ready(function(){-->
<#--        $('#analisis').click(function () {-->
<#--            var elem = document.getElementById('qrcode');-->
<#--            elem.parentNode.removeChild(elem);-->
<#--            var html = document.createElement('div');-->
<#--            html.id = 'qrcode';-->
<#--            document.getElementById('qr').appendChild(html);-->
<#--            var url = ;-->
<#--            new QRCode(document.getElementById("qrcode"), "/shorty.com/" + url);-->

<#--            var qrcode = new QRCode("test", {-->
<#--                text: "test",-->
<#--                width: 128,-->
<#--                height: 128,-->
<#--                colorDark: "#000000",-->
<#--                colorLight: "#ffffff",-->
<#--                correctLevel: QRCode.CorrectLevel.H,-->
<#--                margin: 20-->
<#--            });-->
<#--        });-->
<#--    });-->
<#--</script>-->

</body>
