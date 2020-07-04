/**
 * This module enhances the module {@link org.slf4j} with the capability of
 * formatting parameters using Ansi escape codes, which are defined as <i>styles</i> using
 * the {@link org.fusesource.jansi} module.
 */
module slf4jansi {

    exports slf4jansi;

    requires transitive org.slf4j;
    requires org.fusesource.jansi;

}