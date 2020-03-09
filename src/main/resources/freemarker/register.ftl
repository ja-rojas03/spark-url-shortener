<!DOCTYPE html>
<html lang="en">
<#include 'head.ftl'>
<body>
<#include 'navbar.ftl'>
<div class="signup-form col-md-6" style="margin: auto; display: block">
    <br/><br/><br/><br/>
    <h1 class="text-center">Be part of the shortener family⚡️</h1>
    <br/>
    <form action="/register" method="post">
        <#if warningText != "">
            <p class="text-danger">${warningText}</p>
        </#if>
        <div class="form-group">
            <div class="row">
                <div class="col-md-6"><input type="text" class="form-control" name="first_name" placeholder="First Name" required="required"></div>
                <div class="col-md-6"><input type="text" class="form-control" name="last_name" placeholder="Last Name" required="required"></div>
            </div>
        </div>
        <div class="form-group">
            <input type="text" class="form-control" name="username" placeholder="Username" required="required">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="password" placeholder="Password" required="required">
        </div>
        <div class="form-group">
            <input type="password" class="form-control" name="confirm_password" placeholder="Confirm Password" required="required">
        </div>
        <div class="form-group">
            <label class="checkbox-inline"><input type="checkbox" required="required"> I accept the Terms of Use &amp; Privacy Policy</label>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-block">Register Now</button>
        </div>
    </form>
    <div class="text-center">Already have an account? <a href="/login">Log in</a></div>
</div>
</body>
</html>
