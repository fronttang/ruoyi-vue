<template>
    <div class="app-container">
        <onlyoffice-editor :src="src" :config="config" @ready="onDocumentReady" />
    </div> 
</template>
<script>

export default{
    name: 'onlyoffice',
    data() {
        return {
            src: 'http://10.0.0.103:8084/web-apps/apps/api/documents/api.js',
            config: {
                document: {
                    fileType: "docx",
                    key: "Khirz6zTPdfdasdf7",
                    title: "电气检测初检报告.docx",
                    url: "http://192.168.64.1:8080/profile/upload/2024/07/21/20240721155308759550A60664BE84B8EB85FF611AC984E16.docx"
                },
                documentType: "word",
                editorConfig: {
                    callbackUrl: "http://10.0.0.103:8080/onlyoffice/callback"
                }
            }
        }
    },
    methods: {
        onDocumentReady() {
            console.log("Document is loaded");
        }
    },
};
</script>
