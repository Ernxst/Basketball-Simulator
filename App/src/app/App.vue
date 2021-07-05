<template>
    <router-view v-slot="{ Component }">
        <transition>
            <component :is="Component"/>
        </transition>
    </router-view>
</template>

<script>
export default {
    name: 'App',
    watch: {
        $route(to, from) {
            if (to.hash) {
                this.$nextTick(() => {
                    this.scrollToId(to.hash.slice(1));
                });
            }
        }
    },
    methods: {
        scrollToId(id) {
            try {
                const element = document.getElementById(id);
                window.scrollTo({
                    top: element.offsetTop - 72,
                    behaviour: "smooth",
                });
            } catch {
                location.hash = id;
            }
        }
    },
};
</script>

<style scoped>

</style>
