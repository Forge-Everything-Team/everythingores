package dev.forgeeverything.everythingores.client;

import java.lang.reflect.Method;

/**
 * Asks Iris whether a shader pack is active, without depending on Iris.
 *
 * Shader packs render fog their own way, so the sulfur caves haze is tuned
 * lighter while one is in use. If Iris is absent or the lookup fails, this
 * reports no shader pack and everything falls back to the vanilla tuning.
 */
final class IrisHook {

    private IrisHook() {}

    private static Object api;
    private static Method inUse;
    private static boolean resolved;

    static boolean shaderPackInUse() {
        if (!resolved) {
            resolved = true;
            try {
                Class<?> cls = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
                api = cls.getMethod("getInstance").invoke(null);
                inUse = cls.getMethod("isShaderPackInUse");
            } catch (ReflectiveOperationException | LinkageError e) {
                api = null; // Iris not installed
            }
        }
        if (api == null) return false;
        try {
            return (boolean) inUse.invoke(api);
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }
}
