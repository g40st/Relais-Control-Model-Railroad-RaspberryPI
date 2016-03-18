import com.pi4j.gpio.extension.mcp.MCP23S17GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23S17Pin;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.spi.SpiChannel;

import java.io.IOException;

public class MCP23S17_Switch {

    // create gpio controller
    final GpioController gpio = GpioFactory.getInstance();

    GpioPinDigitalOutput gpio_a[] = new GpioPinDigitalOutput[8];
    GpioPinDigitalOutput gpio_b[] = new GpioPinDigitalOutput[8];
    
    public MCP23S17_Switch() throws InterruptedException, IOException {
        
        System.out.println("MCP23017_Switch");
        
        // create custom MCP23017 GPIO provider
        final MCP23S17GpioProvider gpioProvider = new MCP23S17GpioProvider(MCP23S17GpioProvider.DEFAULT_ADDRESS, SpiChannel.CS1);
        
        // provision gpio output pins and make sure they are all LOW at startup
        gpio_a[0] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A0, "MyOutput-A0", PinState.HIGH);
        gpio_a[1] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A1, "MyOutput-A1", PinState.HIGH);
        gpio_a[2] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A2, "MyOutput-A2", PinState.HIGH);
        gpio_a[3] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A3, "MyOutput-A3", PinState.HIGH);
        gpio_a[4] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A4, "MyOutput-A4", PinState.HIGH);
        gpio_a[5] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A5, "MyOutput-A5", PinState.HIGH);
        gpio_a[6] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A6, "MyOutput-A6", PinState.HIGH);
        gpio_a[7] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A7, "MyOutput-A7", PinState.HIGH);

        // provision gpio output pins and make sure they are all LOW at startup 
        gpio_b[0] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B0, "MyOutput-B0", PinState.HIGH);
        gpio_b[1] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B1, "MyOutput-B1", PinState.HIGH);
        gpio_b[2] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B2, "MyOutput-B2", PinState.HIGH);
        gpio_b[3] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B3, "MyOutput-B3", PinState.HIGH);
        gpio_b[4] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B4, "MyOutput-B4", PinState.HIGH);
        gpio_b[5] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B5, "MyOutput-B5", PinState.HIGH);
        gpio_b[6] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B6, "MyOutput-B6", PinState.LOW);
        gpio_b[7] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B7, "MyOutput-B7", PinState.LOW);

        // Weichen auf Ausgangsposition stellen
        for (int i = 0; i < 12; i= i+2) {
            if(i < 8) {
                gpio.setState(false, gpio_a[i]);
            } else {
                gpio.setState(false, gpio_b[i-8]);
            }
        }
        Thread.sleep(100);
        for (int i = 0; i < 12; i= i+2) {
            if(i < 8) {
                gpio.setState(true, gpio_a[i]);
            } else {
                gpio.setState(true, gpio_b[i-8]);
            }
        }
    }

    // Ein
    public void changeState(int i) throws InterruptedException, IOException {
        if(i < 8) {
            gpio.setState(false, gpio_a[i]);
            Thread.sleep(150);
            gpio.setState(true, gpio_a[i]);
        } else {
            gpio.setState(false, gpio_b[i-8]);
            Thread.sleep(150);
            gpio.setState(true, gpio_b[i-8]);
        }
    }
}