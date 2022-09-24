<template>
  <div>
    <Select label="Discord Server" :options="options" v-model="selected" />
    <BaseCard />
  </div>
</template>

<script setup lang="ts">
import { Option } from "~/modules/shared/form/select";

const options: Option[] = [
  {
    label: "Hola",
    value: "hola"
  }
]

const selected = ref(options[0])

watch(selected, (newValue) => console.log(newValue))
</script>
