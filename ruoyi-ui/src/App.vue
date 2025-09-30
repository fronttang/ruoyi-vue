<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
import store from "./store";

export default {
  name: "App",
  components: { ThemePicker },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  },
  computed: {
    projectId() {
      return this.$store.state.settings.projectId;
    },
  },
  watch: {
    // 全局监听项目id
    projectId: {
      handler(newVal, oldVal) {
        //if (!newVal || newVal == oldVal) return;
        // 请求项目菜单路由
        store.dispatch("GeneProAppRoutes", newVal);
      },
      immediate: true,
    },
  }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
