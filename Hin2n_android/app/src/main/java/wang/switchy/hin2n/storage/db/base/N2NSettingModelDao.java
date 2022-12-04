package wang.switchy.hin2n.storage.db.base;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import wang.switchy.hin2n.storage.db.base.model.N2NSettingModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "N2NSettingList".
*/
public class N2NSettingModelDao extends AbstractDao<N2NSettingModel, Long> {

    public static final String TABLENAME = "N2NSettingList";

    /**
     * Properties of entity N2NSettingModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Version = new Property(1, int.class, "version", false, "VERSION");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property IpMode = new Property(3, int.class, "ipMode", false, "IP_MODE");
        public final static Property Ip = new Property(4, String.class, "ip", false, "IP");
        public final static Property Netmask = new Property(5, String.class, "netmask", false, "NETMASK");
        public final static Property Community = new Property(6, String.class, "community", false, "COMMUNITY");
        public final static Property Password = new Property(7, String.class, "password", false, "PASSWORD");
        public final static Property DevDesc = new Property(8, String.class, "devDesc", false, "DEV_DESC");
        public final static Property SuperNode = new Property(9, String.class, "superNode", false, "SUPER_NODE");
        public final static Property MoreSettings = new Property(10, boolean.class, "moreSettings", false, "MORE_SETTINGS");
        public final static Property SuperNodeBackup = new Property(11, String.class, "superNodeBackup", false, "SUPER_NODE_BACKUP");
        public final static Property MacAddr = new Property(12, String.class, "macAddr", false, "MAC_ADDR");
        public final static Property Mtu = new Property(13, int.class, "mtu", false, "MTU");
        public final static Property LocalIP = new Property(14, String.class, "localIP", false, "LOCAL_IP");
        public final static Property HolePunchInterval = new Property(15, int.class, "holePunchInterval", false, "HOLE_PUNCH_INTERVAL");
        public final static Property ResoveSupernodeIP = new Property(16, boolean.class, "resoveSupernodeIP", false, "RESOVE_SUPERNODE_IP");
        public final static Property LocalPort = new Property(17, int.class, "localPort", false, "LOCAL_PORT");
        public final static Property AllowRouting = new Property(18, boolean.class, "allowRouting", false, "ALLOW_ROUTING");
        public final static Property DropMuticast = new Property(19, boolean.class, "dropMuticast", false, "DROP_MUTICAST");
        public final static Property UseHttpTunnel = new Property(20, boolean.class, "useHttpTunnel", false, "USE_HTTP_TUNNEL");
        public final static Property TraceLevel = new Property(21, int.class, "traceLevel", false, "TRACE_LEVEL");
        public final static Property IsSelcected = new Property(22, boolean.class, "isSelcected", false, "IS_SELCECTED");
        public final static Property GatewayIp = new Property(23, String.class, "gatewayIp", false, "GATEWAY_IP");
        public final static Property DnsServer = new Property(24, String.class, "dnsServer", false, "DNS_SERVER");
        public final static Property EncryptionMode = new Property(25, String.class, "encryptionMode", false, "ENCRYPTION_MODE");
        public final static Property HeaderEnc = new Property(26, boolean.class, "headerEnc", false, "HEADER_ENC");
        public final static Property Proxy = new Property(27, String.class, "proxy", false, "PROXY");
        public final static Property Routes = new Property(28, String.class, "routes", false, "ROUTES");
    }


    public N2NSettingModelDao(DaoConfig config) {
        super(config);
    }
    
    public N2NSettingModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"N2NSettingList\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"VERSION\" INTEGER NOT NULL ," + // 1: version
                "\"NAME\" TEXT," + // 2: name
                "\"IP_MODE\" INTEGER NOT NULL ," + // 3: ipMode
                "\"IP\" TEXT," + // 4: ip
                "\"NETMASK\" TEXT," + // 5: netmask
                "\"COMMUNITY\" TEXT," + // 6: community
                "\"PASSWORD\" TEXT," + // 7: password
                "\"DEV_DESC\" TEXT," + // 8: devDesc
                "\"SUPER_NODE\" TEXT," + // 9: superNode
                "\"MORE_SETTINGS\" INTEGER NOT NULL ," + // 10: moreSettings
                "\"SUPER_NODE_BACKUP\" TEXT," + // 11: superNodeBackup
                "\"MAC_ADDR\" TEXT," + // 12: macAddr
                "\"MTU\" INTEGER NOT NULL ," + // 13: mtu
                "\"LOCAL_IP\" TEXT," + // 14: localIP
                "\"HOLE_PUNCH_INTERVAL\" INTEGER NOT NULL ," + // 15: holePunchInterval
                "\"RESOVE_SUPERNODE_IP\" INTEGER NOT NULL ," + // 16: resoveSupernodeIP
                "\"LOCAL_PORT\" INTEGER NOT NULL ," + // 17: localPort
                "\"ALLOW_ROUTING\" INTEGER NOT NULL ," + // 18: allowRouting
                "\"DROP_MUTICAST\" INTEGER NOT NULL ," + // 19: dropMuticast
                "\"USE_HTTP_TUNNEL\" INTEGER NOT NULL ," + // 20: useHttpTunnel
                "\"TRACE_LEVEL\" INTEGER NOT NULL ," + // 21: traceLevel
                "\"IS_SELCECTED\" INTEGER NOT NULL ," + // 22: isSelcected
                "\"GATEWAY_IP\" TEXT," + // 23: gatewayIp
                "\"DNS_SERVER\" TEXT," + // 24: dnsServer
                "\"ENCRYPTION_MODE\" TEXT," + // 25: encryptionMode
                "\"HEADER_ENC\" INTEGER NOT NULL ," + // 26: headerEnc
                "\"PROXY\" TEXT," + // 27: proxy
                "\"ROUTES\" TEXT);"); // 28: routes
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"N2NSettingList\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, N2NSettingModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getVersion());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getIpMode());
 
        String ip = entity.getIp();
        if (ip != null) {
            stmt.bindString(5, ip);
        }
 
        String netmask = entity.getNetmask();
        if (netmask != null) {
            stmt.bindString(6, netmask);
        }
 
        String community = entity.getCommunity();
        if (community != null) {
            stmt.bindString(7, community);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(8, password);
        }
 
        String devDesc = entity.getDevDesc();
        if (devDesc != null) {
            stmt.bindString(9, devDesc);
        }
 
        String superNode = entity.getSuperNode();
        if (superNode != null) {
            stmt.bindString(10, superNode);
        }
        stmt.bindLong(11, entity.getMoreSettings() ? 1L: 0L);
 
        String superNodeBackup = entity.getSuperNodeBackup();
        if (superNodeBackup != null) {
            stmt.bindString(12, superNodeBackup);
        }
 
        String macAddr = entity.getMacAddr();
        if (macAddr != null) {
            stmt.bindString(13, macAddr);
        }
        stmt.bindLong(14, entity.getMtu());
 
        String localIP = entity.getLocalIP();
        if (localIP != null) {
            stmt.bindString(15, localIP);
        }
        stmt.bindLong(16, entity.getHolePunchInterval());
        stmt.bindLong(17, entity.getResoveSupernodeIP() ? 1L: 0L);
        stmt.bindLong(18, entity.getLocalPort());
        stmt.bindLong(19, entity.getAllowRouting() ? 1L: 0L);
        stmt.bindLong(20, entity.getDropMuticast() ? 1L: 0L);
        stmt.bindLong(21, entity.getUseHttpTunnel() ? 1L: 0L);
        stmt.bindLong(22, entity.getTraceLevel());
        stmt.bindLong(23, entity.getIsSelcected() ? 1L: 0L);
 
        String gatewayIp = entity.getGatewayIp();
        if (gatewayIp != null) {
            stmt.bindString(24, gatewayIp);
        }
 
        String dnsServer = entity.getDnsServer();
        if (dnsServer != null) {
            stmt.bindString(25, dnsServer);
        }
 
        String encryptionMode = entity.getEncryptionMode();
        if (encryptionMode != null) {
            stmt.bindString(26, encryptionMode);
        }
        stmt.bindLong(27, entity.getHeaderEnc() ? 1L: 0L);
 
        String proxy = entity.getProxy();
        if (proxy != null) {
            stmt.bindString(28, proxy);
        }
 
        String routes = entity.getRoutes();
        if (routes != null) {
            stmt.bindString(29, routes);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, N2NSettingModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getVersion());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
        stmt.bindLong(4, entity.getIpMode());
 
        String ip = entity.getIp();
        if (ip != null) {
            stmt.bindString(5, ip);
        }
 
        String netmask = entity.getNetmask();
        if (netmask != null) {
            stmt.bindString(6, netmask);
        }
 
        String community = entity.getCommunity();
        if (community != null) {
            stmt.bindString(7, community);
        }
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(8, password);
        }
 
        String devDesc = entity.getDevDesc();
        if (devDesc != null) {
            stmt.bindString(9, devDesc);
        }
 
        String superNode = entity.getSuperNode();
        if (superNode != null) {
            stmt.bindString(10, superNode);
        }
        stmt.bindLong(11, entity.getMoreSettings() ? 1L: 0L);
 
        String superNodeBackup = entity.getSuperNodeBackup();
        if (superNodeBackup != null) {
            stmt.bindString(12, superNodeBackup);
        }
 
        String macAddr = entity.getMacAddr();
        if (macAddr != null) {
            stmt.bindString(13, macAddr);
        }
        stmt.bindLong(14, entity.getMtu());
 
        String localIP = entity.getLocalIP();
        if (localIP != null) {
            stmt.bindString(15, localIP);
        }
        stmt.bindLong(16, entity.getHolePunchInterval());
        stmt.bindLong(17, entity.getResoveSupernodeIP() ? 1L: 0L);
        stmt.bindLong(18, entity.getLocalPort());
        stmt.bindLong(19, entity.getAllowRouting() ? 1L: 0L);
        stmt.bindLong(20, entity.getDropMuticast() ? 1L: 0L);
        stmt.bindLong(21, entity.getUseHttpTunnel() ? 1L: 0L);
        stmt.bindLong(22, entity.getTraceLevel());
        stmt.bindLong(23, entity.getIsSelcected() ? 1L: 0L);
 
        String gatewayIp = entity.getGatewayIp();
        if (gatewayIp != null) {
            stmt.bindString(24, gatewayIp);
        }
 
        String dnsServer = entity.getDnsServer();
        if (dnsServer != null) {
            stmt.bindString(25, dnsServer);
        }
 
        String encryptionMode = entity.getEncryptionMode();
        if (encryptionMode != null) {
            stmt.bindString(26, encryptionMode);
        }
        stmt.bindLong(27, entity.getHeaderEnc() ? 1L: 0L);
 
        String proxy = entity.getProxy();
        if (proxy != null) {
            stmt.bindString(28, proxy);
        }
 
        String routes = entity.getRoutes();
        if (routes != null) {
            stmt.bindString(29, routes);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public N2NSettingModel readEntity(Cursor cursor, int offset) {
        N2NSettingModel entity = new N2NSettingModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // version
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.getInt(offset + 3), // ipMode
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // ip
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // netmask
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // community
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // password
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // devDesc
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // superNode
            cursor.getShort(offset + 10) != 0, // moreSettings
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // superNodeBackup
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // macAddr
            cursor.getInt(offset + 13), // mtu
            cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14), // localIP
            cursor.getInt(offset + 15), // holePunchInterval
            cursor.getShort(offset + 16) != 0, // resoveSupernodeIP
            cursor.getInt(offset + 17), // localPort
            cursor.getShort(offset + 18) != 0, // allowRouting
            cursor.getShort(offset + 19) != 0, // dropMuticast
            cursor.getShort(offset + 20) != 0, // useHttpTunnel
            cursor.getInt(offset + 21), // traceLevel
            cursor.getShort(offset + 22) != 0, // isSelcected
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // gatewayIp
            cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24), // dnsServer
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // encryptionMode
            cursor.getShort(offset + 26) != 0, // headerEnc
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // proxy
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28) // routes
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, N2NSettingModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setVersion(cursor.getInt(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setIpMode(cursor.getInt(offset + 3));
        entity.setIp(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setNetmask(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCommunity(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setPassword(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setDevDesc(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setSuperNode(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setMoreSettings(cursor.getShort(offset + 10) != 0);
        entity.setSuperNodeBackup(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setMacAddr(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setMtu(cursor.getInt(offset + 13));
        entity.setLocalIP(cursor.isNull(offset + 14) ? null : cursor.getString(offset + 14));
        entity.setHolePunchInterval(cursor.getInt(offset + 15));
        entity.setResoveSupernodeIP(cursor.getShort(offset + 16) != 0);
        entity.setLocalPort(cursor.getInt(offset + 17));
        entity.setAllowRouting(cursor.getShort(offset + 18) != 0);
        entity.setDropMuticast(cursor.getShort(offset + 19) != 0);
        entity.setUseHttpTunnel(cursor.getShort(offset + 20) != 0);
        entity.setTraceLevel(cursor.getInt(offset + 21));
        entity.setIsSelcected(cursor.getShort(offset + 22) != 0);
        entity.setGatewayIp(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setDnsServer(cursor.isNull(offset + 24) ? null : cursor.getString(offset + 24));
        entity.setEncryptionMode(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setHeaderEnc(cursor.getShort(offset + 26) != 0);
        entity.setProxy(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setRoutes(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(N2NSettingModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(N2NSettingModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(N2NSettingModel entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
