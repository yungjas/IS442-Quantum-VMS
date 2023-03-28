<template></template>

<script>
export default {
    data () {
        data: JSON.parse(localStorage.editForm),
        userType: localStorage.userType,

    },
    methods: {
        updateForm () {

        }
    },
    created() {
        try {
            this.get
        } catch (e) {
            if (e instanceof SyntaxError) {
        this.$router.push({ name: "Login" });
      } else {
        console.log(e);
      }
        }
    }
}
</script>
