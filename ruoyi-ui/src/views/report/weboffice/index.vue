<template>
        <inner-link 
            key="webofficeiframe"
            iframeId="webofficeiframe"
            src="/report/weboffice/weboffice"
            ></inner-link>
</template>
<script>
import InnerLink from "@/layout/components/InnerLink/index";

export default {
  components: { InnerLink },
  methods: {
    iframeUrl() {
      return "/report/weboffice/weboffice";
    }
  }
}
</script>
