library ieee;
use ieee.std_logic_1164.all;
entity tri_s is
port( datain:in std_logic_vector(7 downto 0);
		En:in std_logic;
		dataout:out std_logic_vector(7 downto 0)
);
end tri_s;
architecture bhv of tri_s is
begin
process (En,datain)
begin
	if En='1' then
		dataout<=datain;
	else
		dataout<=(others=>'Z');
	end if;
end process;
end bhv;