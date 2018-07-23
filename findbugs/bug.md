1. 流的bug隐患。
    ```java
    File file = new File(path);
    BufferedReader reader = null;
    reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8")); 

    OutputStreamWriter out = null;
    out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
    
    finally{
        if(reader!=null){
            reader.close();
        }
        if(out!=null){
            out.close;
        }
    }
    ```