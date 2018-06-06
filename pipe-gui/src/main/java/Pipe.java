import pipe.actions.gui.PipeApplicationModel;
import pipe.controllers.application.PipeApplicationController;
import pipe.views.PipeApplicationBuilder;
import pipe.views.PipeApplicationView;
import uk.ac.imperial.pipe.parsers.UnparsableException;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;

public final class Pipe {

    protected static PipeApplicationView applicationView;

    private Pipe(String version, String pathToFile) {
        PipeApplicationModel applicationModel = new PipeApplicationModel(version);
        PipeApplicationController applicationController = new PipeApplicationController(applicationModel);
        PipeApplicationBuilder builder = new PipeApplicationBuilder();
        applicationView = builder.build(applicationController, applicationModel);
        openTab(applicationController, pathToFile);
    }

    private void openTab(PipeApplicationController applicationController, String pathToFile) {
        if (pathToFile != null) {
            try {
                applicationController.createNewTabFromFile(new File(pathToFile));
            } catch (UnparsableException e) {
                e.printStackTrace();
            }
        } else {
            applicationController.createEmptyPetriNet();
        }
    }

    public static void main(String[] args) {
        String path = null;
        if (args.length > 0) {
            path = args[0];
        }
        Runnable runnable = pipeRunnable(path);
        SwingUtilities.invokeLater(runnable);
    }

    protected static Runnable pipeRunnable(final String path) {
        return new Runnable() {
            @Override
            public void run() {
                new Pipe("v5.0.2", path);
            }
        };
    }

    protected static void runPipeForTesting() throws InterruptedException, InvocationTargetException {
        SwingUtilities.invokeAndWait(pipeRunnable(null));
    }
}
