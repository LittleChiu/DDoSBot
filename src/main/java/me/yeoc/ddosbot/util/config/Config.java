package me.yeoc.ddosbot.util.config;


import com.google.common.base.Charsets;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Config {

    private final ThreadLocal<Yaml> yaml = ThreadLocal.withInitial(() -> {
        Representer representer = new Representer()
        {
            {
                representers.put( Configuration.class, data -> represent( ( (Configuration) data ).self ));
            }
        };

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle( DumperOptions.FlowStyle.BLOCK );

        return new Yaml( new Constructor(), representer, options );
    });

    public void save(Configuration config, File file) throws IOException
    {
        try ( Writer writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), Charsets.UTF_8 ) )
        {
            save( config, writer );
        }
    }

    public void save(Configuration config, Writer writer)
    {
        yaml.get().dump( config.self, writer );
    }

    public Configuration load(File file) throws IOException
    {
        return load( file, null );
    }

    public Configuration load(File file, Configuration defaults) throws IOException
    {
        try ( FileInputStream is = new FileInputStream( file ) )
        {
            return load( is, defaults );
        }
    }

    public Configuration load(Reader reader)
    {
        return load( reader, null );
    }

    @SuppressWarnings("unchecked")
    public Configuration load(Reader reader, Configuration defaults)
    {
        Map<String, Object> map = yaml.get().loadAs( reader, LinkedHashMap.class );
        if ( map == null )
        {
            map = new LinkedHashMap<>();
        }
        return new Configuration( map, defaults );
    }

    public Configuration load(InputStream is)
    {
        return load( is, null );
    }

    @SuppressWarnings("unchecked")
    public Configuration load(InputStream is, Configuration defaults)
    {
        Map<String, Object> map = yaml.get().loadAs( is, LinkedHashMap.class );
        if ( map == null )
        {
            map = new LinkedHashMap<>();
        }
        return new Configuration( map, defaults );
    }

    public Configuration load(String string)
    {
        return load( string, null );
    }

    @SuppressWarnings("unchecked")
    public Configuration load(String string, Configuration defaults)
    {
        Map<String, Object> map = yaml.get().loadAs( string, LinkedHashMap.class );
        if ( map == null )
        {
            map = new LinkedHashMap<>();
        }
        return new Configuration( map, defaults );
    }
}
