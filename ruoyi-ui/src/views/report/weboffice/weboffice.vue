<template>
    <div id="weboffice" :style="'height:' + height"></div>
</template>
<script>
import WebOfficeSDK from './web-office-sdk-solution-v2.0.6.es.js'
import InnerLink from "@/layout/components/InnerLink/index"


export default{
    components: { InnerLink },
    name: 'weboffice',
    data() {
        return {
          height: document.documentElement.clientHeight - 94.5 + "px;",
          instance: null,
          reportId: this.$route.params.reportId,
          type: this.$route.params.type,
          fileId: null,
          officeType: WebOfficeSDK.OfficeType.Writer,
          appId: process.env.VUE_APP_WEB_OFFICE_APP_ID,
        }
    },
    mounted() {
      this.fileId = this.reportId + '_' + this.type;
      
      if(this.type === '3'){
        this.officeType = WebOfficeSDK.OfficeType.Pdf;
      }

      this.instance = WebOfficeSDK.init({
        officeType: this.officeType,
        appId: this.appId,
        fileId: this.fileId,
        mount: "#weboffice",
        commonOptions: {
          isBrowserViewFullscreen: true,
          isIframeViewFullscreen: false
        }
      });
      
    },
    methods: {
    },
    beforeDestroy(){
      if(this.instance != null){
        this.instance.destroy();
      }
    },
};
</script>
