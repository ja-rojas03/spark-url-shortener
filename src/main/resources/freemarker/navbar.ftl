<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
    <div class="container">
        <a class="navbar-brand" href="/">Spark URL Shortener⚡️</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <#if user??>
            <p class="nav-item nav-link"style="color: white; margin: 0px;">Welcome ${user.username}🏠</p>
        </#if>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ml-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/">Home
                        <span class="sr-only">(current)</span>
                    </a>
                </li>
                <#if user??>
                    <#if user.administrador>
                        <li class="nav-item active">
                            <a class="nav-link" href="/users">Users</a>
                        </li>
                    </#if>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Log out</a>
                    </li>
                <#else>
                    <li class="nav-item">
                        <a class="nav-link" href="/register">Register</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Log in</a>
                    </li>
                </#if>
            </ul>
        </div>
    </div>
</nav>
