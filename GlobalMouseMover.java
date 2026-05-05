import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GlobalMouseMover implements NativeMouseListener {

    private volatile boolean isLeftMouseDown = false;
    private volatile boolean isMacroEnabled = true; // Toggle state for Button 5
    private Robot robot;
    private List<Point.Double> invertedOffsets = new ArrayList<>();

    // PASTE YOUR FULL 195 FRAMES HERE
    private static final String RAW_DATA = 
            "1	-4.52734	8.07031	0\n" +
            "2	-3.65234	5.41406	0\n" +
            "3	-2.74609	3.67578	0\n" +
            "4	-1.82422	2.75391	0\n" +
            "5	-1.80859	1.81641	0\n" +
            "6	-0.0078125	0.0078125	0\n" +
            "7	-0.00390625	0.0078125	0\n" +
            "8	1.78906	3.59375	0\n" +
            "9	0	-1.77734	0\n" +
            "10	0	-3.60547	0\n" +
            "11	0	-5.41797	0\n" +
            "12	0	-6.27734	0\n" +
            "13	-0.0234375	0.0117188	0\n" +
            "14	0	-5.39062	0\n" +
            "15	0	-10.7812	0\n" +
            "16	0	-13.5078	0\n" +
            "17	0	-16.1914	0\n" +
            "18	0	-17.0586	0\n" +
            "19	0	-17.0508	0\n" +
            "20	0.03125	-14.3945	0\n" +
            "21	0	-21.543	0\n" +
            "22	0	-25.1367	0\n" +
            "23	0	-27.793	0\n" +
            "24	0	-28.7188	0\n" +
            "25	0	-28.7148	0\n" +
            "26	0	-28.793	0\n" +
            "27	0	-33.2891	0\n" +
            "28	-1.79297	-36.9336	0\n" +
            "29	-2.70312	-38.7031	0\n" +
            "30	-2.69141	-39.6172	0\n" +
            "31	-2.71484	-39.6172	0\n" +
            "32	-3.61328	-38.6992	0\n" +
            "33	-4.51172	-43.1953	0\n" +
            "34	-4.51953	-45.9219	0\n" +
            "35	-5.41016	-48.6172	0\n" +
            "36	-6.30859	-49.4922	0\n" +
            "37	-6.30469	-49.4922	0\n" +
            "38	-5.42578	-45	0\n" +
            "39	-7.25	-51.293	0\n" +
            "40	-9.00781	-55.7734	0\n" +
            "41	-9.92969	-56.7188	0\n" +
            "42	-9.90625	-56.7148	0\n" +
            "43	-10.7617	-55.7852	0\n" +
            "44	-10.8008	-54	0\n" +
            "45	-9	-59.375	0\n" +
            "46	-8.09375	-61.1797	0\n" +
            "47	-7.21484	-62.082	0\n" +
            "48	-6.30859	-62.0508	0\n" +
            "49	-5.39844	-61.1758	0\n" +
            "50	-6.33203	-57.5898	0\n" +
            "51	-0.0117188	-61.1836	0\n" +
            "52	2.66797	-62.0938	0\n" +
            "53	5.38672	-63	0\n" +
            "54	8.05078	-61.2188	0\n" +
            "55	8.98828	-60.2891	0\n" +
            "56	9.90234	-56.707	0\n" +
            "57	12.5977	-61.1836	0\n" +
            "58	14.4023	-63	0\n" +
            "59	15.3125	-63	0\n" +
            "60	16.2188	-63	0\n" +
            "61	16.1758	-61.1836	0\n" +
            "62	16.2188	-57.5859	0\n" +
            "63	16.2031	-62.0977	0\n" +
            "64	15.3164	-64.7969	0\n" +
            "65	14.4023	-65.6758	0\n" +
            "66	13.5117	-66.6055	0\n" +
            "67	12.6016	-64.7539	0\n" +
            "68	9.94922	-59.3477	0\n" +
            "69	13.5156	-64.7539	0\n" +
            "70	15.3164	-66.5625	0\n" +
            "71	16.1992	-67.4531	0\n" +
            "72	17.0742	-66.5586	0\n" +
            "73	11.6484	-57.5859	0\n" +
            "74	16.1641	-62.0664	0\n" +
            "75	20.6406	-64.7852	0\n" +
            "76	23.3477	-66.6016	0\n" +
            "77	24.2734	-65.6719	0\n" +
            "78	25.168	-64.8125	0\n" +
            "79	26.0664	-63.918	0\n" +
            "80	24.2617	-58.4961	0\n" +
            "81	26.9609	-63	0\n" +
            "82	27.8477	-65.668	0\n" +
            "83	27.8359	-66.582	0\n" +
            "84	27.8281	-65.6953	0\n" +
            "85	27.8594	-64.832	0\n" +
            "86	26.0781	-61.1328	0\n" +
            "87	22.4727	-65.6562	0\n" +
            "88	19.8008	-67.4648	0\n" +
            "89	18	-67.4766	0\n" +
            "90	16.2383	-67.4727	0\n" +
            "91	14.4219	-65.707	0\n" +
            "92	12.6055	-62.0703	0\n" +
            "93	11.6914	-65.6836	0\n" +
            "94	10.8125	-68.3945	0\n" +
            "95	8.98438	-70.2148	0\n" +
            "96	8.08984	-69.3086	0\n" +
            "97	7.19531	-68.3867	0\n" +
            "98	6.29297	-63	0\n" +
            "99	5.36719	-68.3555	0\n" +
            "100	4.43359	-71.9648	0\n" +
            "101	3.57031	-72	0\n" +
            "102	2.64062	-71.1016	0\n" +
            "103	5.42969	-61.1797	0\n" +
            "104	1.80078	-66.5938	0\n" +
            "105	0.03125	-70.207	0\n" +
            "106	-2.66797	-72.9375	0\n" +
            "107	-4.49219	-72.957	0\n" +
            "108	-5.375	-72	0\n" +
            "109	-6.29688	-70.1992	0\n" +
            "110	-4.52344	-65.6914	0\n" +
            "111	-9.92578	-69.2852	0\n" +
            "112	-13.5117	-70.1914	0\n" +
            "113	-15.3281	-70.2109	0\n" +
            "114	-17.0977	-69.2891	0\n" +
            "115	-19.7656	-58.1328	0\n" +
            "116	-18.9102	-61.207	0\n" +
            "117	-16.1836	-67.5078	0\n" +
            "118	-15.3164	-69.3086	0\n" +
            "119	-13.5469	-69.3125	0\n" +
            "120	-12.6445	-68.3867	0\n" +
            "121	-11.7383	-67.5117	0\n" +
            "122	-9.875	-63	0\n" +
            "123	-11.6992	-67.4961	0\n" +
            "124	-11.7188	-69.2969	0\n" +
            "125	-12.6445	-70.2031	0\n" +
            "126	-12.6016	-70.2148	0\n" +
            "127	-12.6406	-68.4219	0\n" +
            "128	-12.6367	-63	0\n" +
            "129	-11.7109	-68.3984	0\n" +
            "130	-11.7109	-71.0781	0\n" +
            "131	-11.6914	-72	0\n" +
            "132	-10.8281	-71.0703	0\n" +
            "133	-10.8516	-70.1758	0\n" +
            "134	-9.89844	-66.5547	0\n" +
            "135	-9.03125	-71.0469	0\n" +
            "136	-9.04297	-72.8242	0\n" +
            "137	-8.14844	-73.7422	0\n" +
            "138	-8.15234	-73.7617	0\n" +
            "139	-8.14844	-72.0547	0\n" +
            "140	-6.26953	-67.4727	0\n" +
            "141	-8.99609	-71.0664	0\n" +
            "142	-10.7539	-72.8828	0\n" +
            "143	-12.5625	-73.7617	0\n" +
            "144	-13.457	-72.8945	0\n" +
            "145	-13.5	-71.1172	0\n" +
            "146	-13.4727	-67.457	0\n" +
            "147	-15.3047	-71.0508	0\n" +
            "148	-15.2812	-72.8828	0\n" +
            "149	-16.1719	-73.8086	0\n" +
            "150	-16.168	-72.9102	0\n" +
            "151	-16.1953	-71.0938	0\n" +
            "152	-15.2617	-66.5938	0\n" +
            "153	-14.3867	-70.1719	0\n" +
            "154	-12.6406	-73.7852	0\n" +
            "155	-11.6875	-74.6836	0\n" +
            "156	-10.7812	-73.8125	0\n" +
            "157	-9.91016	-72.0547	0\n" +
            "158	-9.06641	-67.4805	0\n" +
            "159	-6.35156	-71.0938	0\n" +
            "160	-2.73438	-72.8789	0\n" +
            "161	-0.0117188	-73.7578	0\n" +
            "162	-0.015625	-71.9727	0\n" +
            "163	1.76562	-71.0898	0\n" +
            "164	2.68359	-66.6055	0\n" +
            "165	8.06641	-69.3047	0\n" +
            "166	10.7812	-69.25	0\n" +
            "167	13.5	-69.2734	0\n" +
            "168	15.2617	-67.4648	0\n" +
            "169	16.2109	-65.6602	0\n" +
            "170	16.1641	-60.2773	0\n" +
            "171	18.8555	-63.8711	0\n" +
            "172	20.6016	-66.5703	0\n" +
            "173	21.5156	-67.4219	0\n" +
            "174	22.4688	-65.7031	0\n" +
            "175	22.4375	-64.8438	0\n" +
            "176	21.6367	-62	0\n" +
            "177	20.668	-64.7422	0\n" +
            "178	18.9336	-68.3633	0\n" +
            "179	18.0234	-68.3477	0\n" +
            "180	16.2734	-68.3828	0\n" +
            "181	15.3477	-67.4375	0\n" +
            "182	14.4453	-64.7891	0\n" +
            "183	13.5391	-63	0\n" +
            "184	12.582	-59.3984	0\n" +
            "185	11.7109	-55.8164	0\n" +
            "186	10.8086	-53.0977	0\n" +
            "187	9.92578	-48.5977	0\n" +
            "188	8.99219	-44.9883	0\n" +
            "189	8.08984	-42.3008	0\n" +
            "190	7.20703	-36.9336	0\n" +
            "191	6.28516	-32.3242	0\n" +
            "192	5.39453	-29.6836	0\n" +
            "193	4.47656	-26.0742	0\n" +
            "194	3.58984	-22.4727	0\n" +
            "195	3.60938	-19.7617	0";


    public GlobalMouseMover() {
        try {
            robot = new Robot();
            parseData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseData() {
        String[] lines = RAW_DATA.trim().split("\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].trim().split("\\s+");
            if (parts.length >= 3) {
                double x = -Double.parseDouble(parts[1]) * 3;
                double y = -Double.parseDouble(parts[2]) * 3;
                invertedOffsets.add(new Point.Double(x, y));
            }
        }
        System.out.println("Loaded " + invertedOffsets.size() + " inverted frames.");
        System.out.println("Macro is starting in ENABLED state. Press Mouse Button 5 to toggle.");
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        // Toggle Enable/Disable on Mouse Button 5
        if (e.getButton() == 5) {
            isMacroEnabled = !isMacroEnabled;
            System.out.println("Macro Enabled: " + isMacroEnabled);
        }

        // Trigger movement on Left Click (Button 1) ONLY if enabled
        if (e.getButton() == NativeMouseEvent.BUTTON1) {
            isLeftMouseDown = true;
            if (isMacroEnabled) {
                startMovementThread();
            }
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        // Stop moving when Left Click is released
        if (e.getButton() == NativeMouseEvent.BUTTON1) {
            isLeftMouseDown = false;
        }
    }

    private void startMovementThread() {
        new Thread(() -> {
            int frameIndex = 0;
            
            // Capture the initial mouse position when the click starts
            Point startPos = MouseInfo.getPointerInfo().getLocation();
            double startX = startPos.getX();
            double startY = startPos.getY();

            // Loop continues as long as left mouse button is held down
            while (isLeftMouseDown) {
                if (frameIndex >= invertedOffsets.size()) {
                    frameIndex = 0; // Loop back to frame 0
                }

                Point.Double offset = invertedOffsets.get(frameIndex);
                
                // Calculate new position based on the starting position + inverted offset
                int newX = (int) Math.round(startX + offset.x);
                int newY = (int) Math.round(startY + offset.y);

                robot.mouseMove(newX, newY);

                frameIndex++;

                try {
                    // 60 FPS = ~16.67ms per frame
                    Thread.sleep(17);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        // Disable JNativeHook logging to prevent console spam
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeMouseListener(new GlobalMouseMover());
    }
}
