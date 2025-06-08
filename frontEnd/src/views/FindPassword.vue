<template>
    <MainHeader />
    <div class="hero bg-base-200 h-screen pb-100 box-border">
        <div class="hero-content flex-col lg:flex-row-reverse">
            <div class="text-center lg:text-left">
                <h1 class="text-5xl font-bold w-100">Regist now!</h1>
            </div>
            <div class="card bg-base-100 w-full max-w-sm shrink-0 shadow-2xl">
                <div class="card-body">
                    <fieldset class="fieldset">
                        <label class="label">당신의 Email이 무엇인가요?</label>
                        <input v-model="userEmail" type="email" class="input" placeholder="Email" />

                        <template>
                            <label class="label">당신이 지정한 NickName이 무엇인가요?</label>
                            <input v-model="userNickName" type="text" class="input" placeholder="NickName" />
                        </template>

                        <template>
                            <br>
                            <label class="label">Password</label>
                            <input v-model="password" type="password" class="input" placeholder="Password" />
                            <label class="label">Password Confirm</label>
                            <input v-model="password2" type="password" class="input" placeholder="Password" />
                            <button @click="onRegist" class="btn btn-neutral mt-4">Change</button>
                        </template>
                    </fieldset>
                </div>
                <div v-html="worning" class="flex flex-col justify-center items-center mb-2"></div>
            </div>
        </div>
    </div>
</template>

<script setup>

</script>