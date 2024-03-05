<template>
    <body>
    <div id="app">
        <h1>欢迎来到-->{{ name }}</h1>
    </div>
    <script type="text/javascript">
        //创建vue对象
        var app = new Vue({
            //让vue接管div标签
            el:"#app",
            //定义数据,里边包含一个属性name,值为"白大锅"
            data:{
                name:"白大锅"
            }
        });
    </script>
</body>
</template>

<script>

</script>

<style>

</style>