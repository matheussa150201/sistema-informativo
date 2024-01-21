package sistema.informativo.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sistema.informativo.service.StorageCloudinaryService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class StorageCloudinaryServiceImpl implements StorageCloudinaryService {

    private final Cloudinary cloudinary;

    Map<String,String> valueMap = new HashMap<>();

    /**
     * Construtor da classe StorageCloudinaryServiceImpl.
     * Inicializa as credenciais necessárias para acessar o serviço Cloudinary.
     */
    public StorageCloudinaryServiceImpl() {
        valueMap.put("cloud_name", "dvcok5rea");
        valueMap.put("api_key", "132341999388235");
        valueMap.put("api_secret", "AsfhduR-LO7gVvibfgX1e-OCLME");
        cloudinary = new Cloudinary(valueMap);
    }

    /**
     * Método responsável por realizar o upload de um arquivo para o serviço Cloudinary.
     *
     * @param multipartFile O arquivo a ser enviado para o Cloudinary.
     * @return Um mapa contendo informações sobre o upload, como URL, identificador público, etc.
     * @throws IOException Exceção lançada em caso de erros na manipulação do arquivo.
     */
    @Override
    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map upload = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return upload;
    }

    /**
     * Método responsável por excluir uma imagem do serviço Cloudinary.
     *
     * @param id O identificador público da imagem no Cloudinary.
     * @return Um mapa contendo informações sobre a exclusão, como sucesso, erro, etc.
     * @throws IOException Exceção lançada em caso de erros na exclusão da imagem.
     */
    @Override
    public Map delete(String id) throws IOException {
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;

    }

    /**
     * Método utilitário para converter um objeto MultipartFile em um arquivo File.
     *
     * @param multipartFile O arquivo multipart a ser convertido.
     * @return Um objeto File representando o conteúdo do MultipartFile.
     * @throws IOException Exceção lançada em caso de erros durante a conversão.
     */
    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fileOutputStream =new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return file;
    }
}