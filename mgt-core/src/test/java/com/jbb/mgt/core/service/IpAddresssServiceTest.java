package com.jbb.mgt.core.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jbb.server.common.Home;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/core-application-config.xml", "/datasource-config.xml"})
public class IpAddresssServiceTest {
    @Autowired
    private IpAddressService ipAddressService;

    @BeforeClass
    public static void oneTimeSetUp() {
        Home.getHomeDir();
    }

    private  long ipToLong(String ipaddress) {
        String[] arr = ipaddress.split("\\.");
        long result = 0;
        for (int i = 0; i <= 3; i++) {
            long ip = Long.parseLong(arr[i]);
            result |= ip << ((3-i) << 3);
        }
        return result;
    }
    
    java.math.BigInteger IPV6LongIP(String ipv6) throws UnknownHostException {
        java.net.InetAddress ia = java.net.InetAddress.getByName(ipv6);
        byte byteArr[] = ia.getAddress();
        java.math.BigInteger ipnumber = null;
        if (ia instanceof java.net.Inet6Address) {
            ipnumber = new java.math.BigInteger(1, byteArr);
        }
        return ipnumber;
    }
    
    java.math.BigInteger IPV4LongIP(String ipv4) throws UnknownHostException {
        java.net.InetAddress ia = java.net.InetAddress.getByName(ipv4);
        byte byteArr[] = ia.getAddress();
        java.math.BigInteger ipnumber = null;
        if (ia instanceof java.net.Inet4Address) {
            ipnumber = new java.math.BigInteger(1, byteArr);
        }
        return ipnumber;
    }
    

    private List<String> readFile(String path) throws IOException {
        List<String> list = new ArrayList<String>();
        FileInputStream fis = new FileInputStream(path);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);

        String line = null;

        while ((line = br.readLine()) != null) {
            list.add(line);
            System.out.println(line);
        }
        br.close();
        isr.close();
        fis.close();
        return list;
    }
    
    public static void writeToFile(String path, List<String> list) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(path));
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);

        for (String arr : list) {
            bw.write(arr + "\n");
        }
        bw.close();
        osw.close();
        fos.close();
    }
    
    
    @Test
    public void testIpAddressInCities() throws IOException {
        
        String ipAddress = "112.97.183.46";
        String[] cities = {"%北京市%","%深圳市%"};
      boolean flag = ipAddressService.checkIpAddressInCities(ipAddress, cities);
      assertTrue(flag);
        
    }
    
    @Test
    public void testIpAddress() throws IOException {
        String ipAddress = "112.97.183.46";

        boolean flag = ipAddressService.checkIpAddress(ipAddress);
        assertTrue(flag);

//        ipAddress = "113.87.187.5";
        
//        System.out.println(IPV4LongIP(ipAddress));
//        
//        flag = ipAddressService.checkIpAddress(IPV4LongIP(ipAddress));
//        assertTrue(flag);
//        
//        String ipV6 = "2001:4860:4860::8888";
//        
//        System.out.println(IPV6LongIP(ipV6));
//
//        flag = ipAddressService.checkIpAddress(IPV4LongIP(ipAddress));
//        assertTrue(flag);
        
//        List<String> lines  = readFile("/Users/VincentTang/Downloads/ip-address-1");
//        
//        List<String> lines2 = new ArrayList<String>();
//        for(String line :lines){
//            try{
//            String f1 = line.substring(0, 16).trim();
//            String f2 = line.substring(16, 32).trim();
//            String ll = line.substring(32).trim();
//             int index =  ll.indexOf(" ");
//            
//            String f3 = ll.substring(0, index);
//            String f4 = ll.substring(index).trim();
//           
//            java.math.BigInteger f1Int = null;
//            java.math.BigInteger f2Int = null;
//            
//           
//                f1Int = IPV4LongIP(f1);
//                f2Int = IPV4LongIP(f2);
//                lines2.add(f1Int+"|"+f2Int+"|"+f1+"|"+f2+"|"+f3+"|"+f4);
//            }catch(Exception e){
//                System.out.println(line);
//                continue;
//            }
//          
////            System.out.println(f1Int+"|"+f2Int+"|"+f1+"|"+f2+"|"+f3+"|"+f4);
//            
//        }
        
//        writeToFile("/Users/VincentTang/Downloads/ip-addresss-2", lines2);
        
        
//        List<String> lines  = readFile("/Users/VincentTang/Downloads/delegated-apnic-latest");
//        for(String line :lines){
//            String [] fields = line.split("\\|");
//            String c = fields[1];
//            String type = fields[2];
//            String ipAddress = fields[3];
//            BigInteger size =BigInteger.valueOf(Long.parseLong(fields[4]));
////            if(!c.equals("CN")){
////                continue;
////            }
//            java.math.BigInteger bigInt = null;
//            java.math.BigInteger bigInt2 = null;
//            if("ipv4".equals(type)){
//                bigInt = IPV4LongIP(ipAddress);
//                bigInt2 = bigInt.add(size);
//                System.out.println(ipAddress+"|"+bigInt+"|"+bigInt2);
//            }else if("ipv6".equals(type)){
//                bigInt =  IPV6LongIP(ipAddress);
//                bigInt2 = bigInt.add(size);
//                System.out.println(ipAddress+"|"+bigInt+"|"+bigInt2);
//            }
//            
//           
//            
//        }
    }
}
