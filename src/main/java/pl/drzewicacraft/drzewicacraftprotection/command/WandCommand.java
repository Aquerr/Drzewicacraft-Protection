package pl.drzewicacraft.drzewicacraftprotection.command;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.noise.module.modifier.ScalePoint;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.manipulator.immutable.ImmutableListData;
import org.spongepowered.api.data.manipulator.mutable.ListData;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import pl.drzewicacraft.drzewicacraftprotection.DrzewicacraftProtection;
import pl.drzewicacraft.drzewicacraftprotection.PluginInfo;

import java.util.ArrayList;
import java.util.List;

public class WandCommand extends AbstractCommand
{
	public WandCommand(final DrzewicacraftProtection plugin)
	{
		super(plugin);
	}

	@Override
	public CommandResult execute(final CommandSource source, final CommandContext args) throws CommandException
	{
		if(!(source instanceof Player))
			throw new CommandException(Text.of("Tylko gracze będący w grze mogą użyć tej komendy!"));

		final Player player = (Player)source;

		final List<Text> wandLore = new ArrayList<>();
		//TODO: Update these text. Make them better.
		wandLore.add(Text.of("Zaznacz pierwszy punkt przy pomocy", TextStyles.BOLD, TextColors.AQUA, " lewego przycisku myszki"));
		wandLore.add(Text.of("Zaznacz drugi punkt przy pomocy", TextStyles.BOLD, TextColors.AQUA, " prawego przycisku myszki"));

		final ItemStack item = ItemStack.builder()
				.itemType(ItemTypes.IRON_SWORD)
				.add(Keys.DISPLAY_NAME, Text.of(TextColors.GOLD, "DrzewicaCraft Wand"))
				.add(Keys.ITEM_LORE, wandLore)
				.itemData(Sponge.getDataManager().getBuilder(ListData.class).get().build(Sponge.getDataManager().createContainer().set(DataQuery.of("drzewica"), true)).get())
				.build();

		final PlayerInventory playerInventory = (PlayerInventory) player.getInventory();
		final Hotbar hotbar = playerInventory.getHotbar();
		boolean wandAdded = false;

		//Add item to first empty slot in hotbar
		for (Inventory inventorySlot : hotbar.slots())
		{
			final Slot slot = (Slot)inventorySlot;
			if (!slot.peek().isPresent())
			{
				slot.offer(item);
				wandAdded = true;
				break;
			}
		}

		if (!wandAdded)
		{
			//Add wand to gridInventory
			player.getInventory().offer(item);
		}

		player.playSound(SoundTypes.BLOCK_ANVIL_USE, player.getLocation().getPosition(), 5, 10);
		player.getWorld().spawnParticles(ParticleEffect.builder().type(ParticleTypes.FIREWORKS_SPARK).quantity(100).offset(new Vector3d(0.5, 1.1, 0.5)).build(), player.getLocation().getPosition());
		player.sendMessage(Text.of(PluginInfo.MESSAGE_PREFIX.concat(Text.of(TextColors.GREEN, "Różdżka DrzewicaCraft została umieszczona w Twoim ekwipunku!"))));

		return CommandResult.success();
	}
}
