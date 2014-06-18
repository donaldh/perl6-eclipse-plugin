package org.perl6.eclipse;

import java.net.URL;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.perl6.script.Perl6ScriptEngine;

public class Activator implements BundleActivator {

    private static BundleContext context;
    private static String prefix;

    static BundleContext getContext() {
        return context;
    }

    public static String getPrefix() {
        return prefix;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
     * )
     */
    public void start(BundleContext bundleContext) throws Exception {
        Activator.context = bundleContext;

        URL bundleUrl = new URL(bundleContext.getBundle().getLocation());
        String bundlePath = new URL(bundleUrl.getPath()).getPath();
        prefix = bundlePath + "install";
        System.setProperty("perl6.prefix", prefix);

        String code = "for 1..10 {\n" + "  say 'Hello Eclipse World!'\n" + "}";
        new Perl6ScriptEngine().eval(code);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext bundleContext) throws Exception {
        Activator.context = null;
    }

}
