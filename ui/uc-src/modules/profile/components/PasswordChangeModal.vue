<script lang="ts" setup>
import { VButton, VModal, VSpace } from "@halo-dev/components";
import SubmitButton from "@/components/button/SubmitButton.vue";
import { ref, watch } from "vue";
import type { User } from "@halo-dev/api-client";
import { apiClient } from "@/utils/api-client";
import { cloneDeep } from "lodash-es";
import { reset } from "@formkit/core";
import { setFocus } from "@/formkit/utils/focus";

const props = withDefaults(
  defineProps<{
    visible: boolean;
    user?: User;
  }>(),
  {
    visible: false,
    user: undefined,
  }
);

const emit = defineEmits<{
  (event: "update:visible", visible: boolean): void;
  (event: "close"): void;
}>();

interface PasswordChangeFormState {
  oldPassword: string;
  password: string;
  password_confirm?: string;
}

const initialFormState: PasswordChangeFormState = {
  oldPassword: "",
  password: "",
  password_confirm: "",
};

const formState = ref<PasswordChangeFormState>(cloneDeep(initialFormState));
const saving = ref(false);

watch(
  () => props.visible,
  (visible) => {
    if (visible) {
      setFocus("passwordInput");
    } else {
      handleResetForm();
    }
  }
);

const onVisibleChange = (visible: boolean) => {
  emit("update:visible", visible);
  if (!visible) {
    emit("close");
  }
};

const handleResetForm = () => {
  formState.value = cloneDeep(initialFormState);
  reset("password-form");
};

const handleChangePassword = async () => {
  try {
    saving.value = true;

    const changeOwnPasswordRequest = cloneDeep(formState.value);
    delete changeOwnPasswordRequest.password_confirm;

    await apiClient.user.changeOwnPassword({
      changeOwnPasswordRequest,
    });

    window.location.reload();
  } catch (e) {
    console.error(e);
  } finally {
    saving.value = false;
  }
};
</script>

<template>
  <VModal
    :visible="visible"
    :width="500"
    :title="$t('core.uc_profile.change_password_modal.title')"
    @update:visible="onVisibleChange"
  >
    <FormKit
      id="password-form"
      v-model="formState"
      name="password-form"
      :actions="false"
      type="form"
      :config="{ validationVisibility: 'submit' }"
      @submit="handleChangePassword"
    >
      <FormKit
        id="passwordInput"
        :label="
          $t('core.uc_profile.change_password_modal.fields.old_password.label')
        "
        name="oldPassword"
        type="password"
        validation="required:trim"
      ></FormKit>
      <FormKit
        :label="
          $t('core.uc_profile.change_password_modal.fields.new_password.label')
        "
        name="password"
        type="password"
        validation="required:trim|length:5,100|matches:/^\S.*\S$/"
        :validation-messages="{
          matches: $t('core.formkit.validation.trim'),
        }"
      ></FormKit>
      <FormKit
        :label="
          $t(
            'core.uc_profile.change_password_modal.fields.confirm_password.label'
          )
        "
        name="password_confirm"
        type="password"
        validation="confirm|required:trim|length:5,100|matches:/^\S.*\S$/"
        :validation-messages="{
          matches: $t('core.formkit.validation.trim'),
        }"
      ></FormKit>
    </FormKit>
    <template #footer>
      <VSpace>
        <SubmitButton
          v-if="visible"
          :loading="saving"
          type="secondary"
          :text="$t('core.common.buttons.submit')"
          @submit="$formkit.submit('password-form')"
        >
        </SubmitButton>
        <VButton @click="onVisibleChange(false)">
          {{ $t("core.common.buttons.cancel_and_shortcut") }}
        </VButton>
      </VSpace>
    </template>
  </VModal>
</template>
