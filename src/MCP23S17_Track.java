import com.pi4j.gpio.extension.mcp.MCP23S17GpioProvider;
import com.pi4j.gpio.extension.mcp.MCP23S17Pin;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.spi.SpiChannel;

import java.io.IOException;

public class MCP23S17_Track {
    final GpioController gpio = GpioFactory.getInstance();

    GpioPinDigitalOutput gpio_a[] = new GpioPinDigitalOutput[8];
    GpioPinDigitalOutput gpio_b[] = new GpioPinDigitalOutput[8];
    
    public MCP23S17_Track() throws InterruptedException, IOException {        
        final MCP23S17GpioProvider gpioProvider = new MCP23S17GpioProvider(MCP23S17GpioProvider.DEFAULT_ADDRESS, SpiChannel.CS0);
        
        gpio_a[0] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A0, "MyOutput-A0", PinState.HIGH);
        gpio_a[1] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A1, "MyOutput-A1", PinState.HIGH);
        gpio_a[2] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A2, "MyOutput-A2", PinState.HIGH);
        gpio_a[3] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A3, "MyOutput-A3", PinState.HIGH);
        gpio_a[4] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A4, "MyOutput-A4", PinState.HIGH);
        gpio_a[5] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A5, "MyOutput-A5", PinState.HIGH);
        gpio_a[6] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A6, "MyOutput-A6", PinState.HIGH);
        gpio_a[7] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_A7, "MyOutput-A7", PinState.HIGH);

        gpio_b[0] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B0, "MyOutput-B0", PinState.HIGH);
        gpio_b[1] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B1, "MyOutput-B1", PinState.LOW);
        gpio_b[2] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B2, "MyOutput-B2", PinState.LOW);
        gpio_b[3] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B3, "MyOutput-B3", PinState.LOW);
        gpio_b[4] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B4, "MyOutput-B4", PinState.LOW);
        gpio_b[5] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B5, "MyOutput-B5", PinState.LOW);
        gpio_b[6] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B6, "MyOutput-B6", PinState.LOW);
        gpio_b[7] = gpio.provisionDigitalOutputPin(gpioProvider, MCP23S17Pin.GPIO_B7, "MyOutput-B7", PinState.LOW);
    }

    public void setActive(int i) {
        if(i < 8) {
            gpio.setState(false, gpio_a[i]);
        } else {
            gpio.setState(false, gpio_b[i-8]);
        }
    }

    public void setInActive(int i) {
        if(i < 8) {
            gpio.setState(true, gpio_a[i]);
        } else {
            gpio.setState(true, gpio_b[i-8]);
        }
    }
}