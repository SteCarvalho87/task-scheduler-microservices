package com.stephanie.usuario.business;

import com.stephanie.usuario.business.converter.UsuarioConverter;
import com.stephanie.usuario.business.dto.EnderecoDTO;
import com.stephanie.usuario.business.dto.TelefoneDTO;
import com.stephanie.usuario.business.dto.UsuarioDTO;
import com.stephanie.usuario.infrastructure.entity.Endereco;
import com.stephanie.usuario.infrastructure.entity.Telefone;
import com.stephanie.usuario.infrastructure.entity.Usuario;
import com.stephanie.usuario.infrastructure.exceptions.ConflictException;
import com.stephanie.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.stephanie.usuario.infrastructure.repository.EnderecoRepository;
import com.stephanie.usuario.infrastructure.repository.TelefoneRepository;
import com.stephanie.usuario.infrastructure.repository.UsuarioRepository;
import com.stephanie.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("E-mail já cadastrado: " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("E-mail já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);

    }

    public UsuarioDTO buscarUsuarioPorEmail(String email){
        try{
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(() -> new ResourceNotFoundException("Email não encontrado: " + email)
                            )
            );
        }catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("Email não encontrado: " + email);
        }
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO dto){
       //remove obrigatoriedade de passar o email novamente
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //Criptografia da senha
        dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);

        //busca dados do usuario
       Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não localizado"));

       //mescla dados da requisição com os dados do banco
       Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);

       //Salva dados do usuario convertidos e converte resultado para usuarioDTO
       return usuarioConverter.paraUsuarioDTO((usuarioRepository.save(usuario)));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO){
        Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado" + idEndereco));

        Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO,entity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO dto){
        Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() -> new ResourceNotFoundException("Id não encontrado" + idTelefone));

        Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email não encontrado" + email));

        Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}