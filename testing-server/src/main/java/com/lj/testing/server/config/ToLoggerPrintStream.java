package com.lj.testing.server.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

@Slf4j
public class ToLoggerPrintStream {

    private PrintStream myPrintStream;

    /**
     * @return printStream
     */
    public PrintStream getPrintStream() {
        if (myPrintStream == null) {
            OutputStream output = new OutputStream() {

                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                @Override
                public void write(int b) throws IOException {

                    baos.write(b);

                }

                /**
                 * @see java.io.OutputStream#flush()
                 */
                @Override
                public void flush() {

                    String myLog = this.baos.toString().trim();

                    if (StringUtils.isNotBlank(myLog)) {
                        log.info(myLog);
                        baos = new ByteArrayOutputStream();
                    }
                }
            };
            // true: autoflush
            // must be set!
            myPrintStream = new PrintStream(output, true);

        }
        return myPrintStream;
    }

}
