<template>
    <router-view v-slot="{ Component }">
        <transition>
            <div>
                <img v-if="background !== ''" :src="background" alt="Background"
                     class="background-img centred">
                <component :is="Component"/>
            </div>
        </transition>
    </router-view>
</template>

<script>
export default {
    name: 'App',
    data() {
        return {
            background: ""
        };
    },
    beforeCreate() {
        this.$store.dispatch("media/fetchBackgrounds").then(_ => {
            this.setBackground();
        });
    },
    watch: {
        $route(to, from) {
            this.setBackground();
            if (to.hash) {
                this.$nextTick(() => {
                    this.scrollToId(to.hash.slice(1));
                });
            }
        }
    },
    methods: {
        setBackground() {
            const src = this.$route.name;
            this.background = this.$store.getters["media/backgrounds"][src] || "";
        },
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
