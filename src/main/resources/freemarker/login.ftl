<!DOCTYPE html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="container">
    <br/><br/><br/><br/>
    <h1 class="my-4" style="text-align: center;">Log in to your Spark Shortener⚡️account️</h1>
    <br/>
    <div class="login-form col-md-6" style="margin: auto; display: block">
        <form action="/login" method="post">
            <#if warningText != "">
                <p class="text-danger text-center">${warningText}</p>
            </#if>
            <div class="form-group">
                <input type="text" class="form-control" name="username" placeholder="Username" required="required">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" name="password" placeholder="Password" required="required">
            </div>
            <div class="clearfix">
                <label class="pull-left checkbox -inline"><input type="checkbox" name="remember"> Remember me</label>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary btn-block">Log in</button>
            </div>
        </form>
        <p>You don't have an account?!😯<a href="/register">Let's go and create one😌</a></p>
    </div>
</div>
</body>
</html>
