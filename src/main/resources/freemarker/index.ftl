<!DOCTYPE html>
<html lang="en">
<#include 'head.ftl'>
<body>

<#include 'navbar.ftl'>

<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-md-10">
            <br><br>
            <h4 class="my-4">Shorten your link</h4>
            <!-- Post Creation Form -->
            <form method="post" action="/createUrl" class="form-inline">
                <div class="col-md-10 form-group">
                    <input class="form-control col-md-10" name="originalUrl" placeholder="https://spark-url-shortener.herokuapp.com" type="text">
                    <button id="postPost" type="submit" class="btn btn-primary col-md-2" style="border-radius: 5px;">Shorten me!</button>
                </div>
            </form>
            <br>

            <div id="qr">
                <div id="qrcode"></div>
            </div>

            <h2 class="my-4">All posted links🎊</h2>
            <!--Shortened links table -->
            <table class="table">
                <thead>
                <tr>
                    <th>Original</th>
                    <th>Shortened</th>
                    <th>Created By</th>
<#--                    <th>QR Code</th>-->
                    <th>Stats</th>
                </tr>
                </thead>
                <tbody>
                    <#list links as link>
                        <tr>
                            <#if link.descriptionPreview?contains("(403)")>
                                <td><img style="width: 100px; height: auto;" src="${link.iamgePreview}"><p style="color: orange">The shortened link didn't had an image preview</p></td>
                            <#else>
                                <td><img style="width: 100px; height: auto;" src="${link.iamgePreview}">${link.descriptionPreview}</td>
                            </#if>
                        </tr>

                        <tr>
                            <td><a href="/clicky.com/${link.urlBase62?substring(12)}">${link.urlOriginal}</a></td>
                            <td><a href="/clicky.com/${link.urlBase62?substring(12)}">${link.urlBase62}</a></td>
                            <#if creador== "none">
                            <td>Unknown</td>
                            <#else>
                            <td>${link.creador.username}</td>
                            </#if>
                            <td>
                                <#if user??>
<#--                                    <a href="/stats/${link.urlIndexada}">Stats</a>-->
                                    <div id="qrcode${link.urlBase62?substring(12)}"></div>
                                    <img alt="qrcode" src="https://quickchart.io/qr?text=https://spark-url-shortener.herokuapp.com/stats/${link.urlIndexada}" />
                                <#else>
                                    <p style="color:orange;font-size: 14px;">Log in to see stats!👍🏼</p>
                                </#if>
                            </td>
                        </tr>
                    </#list>

                </tbody>
            </table>

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
